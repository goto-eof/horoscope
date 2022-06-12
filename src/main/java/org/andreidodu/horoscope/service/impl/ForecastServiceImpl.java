package org.andreidodu.horoscope.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

import org.andreidodu.horoscope.entities.Forecast;
import org.andreidodu.horoscope.repository.ForecastDao;
import org.andreidodu.horoscope.repository.ForecastSignDao;
import org.andreidodu.horoscope.service.ForecastService;
import org.andreidodu.horoscope.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ForecastServiceImpl implements ForecastService {

	private static final Logger LOG = LoggerFactory.getLogger(ForecastServiceImpl.class);

	private static final String CATEGORY_HEALTH = "health";
	private static final String CATEGORY_LOVE = "love";
	private static final String CATEGORY_MONEY = "money";

	private static final List<String> HOROSCOPE_CATEGORIES = Arrays.asList(CATEGORY_HEALTH, CATEGORY_LOVE,
			CATEGORY_MONEY);
	private static final List<String> ZODIAC_SIGNS = Arrays.asList("aries", "taurus", "gemini", "cancer", "leo",
			"virgo", "libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces");

	@Autowired
	private ForecastDao forecastDao;

	@Autowired
	private ForecastSignDao forecastSignDao;

	@Autowired
	private Environment env;

	@Override
	public String retrieveBySing(String sign) {

		this.validateSign(sign);

		// if we have some records for today, then it means that we can retrieve and
		// return those records, else we should generate them (I will change it in the
		// future)
		Date today = DateUtils.truncateDate(new Date());
		boolean thereAre = this.forecastSignDao.thereAreRecordsForInterval(sign, today, DateUtils.addDays(today, 1),
				HOROSCOPE_CATEGORIES);

		LOG.debug("thereAre: {}", thereAre);

		if (thereAre) {
			LOG.debug("I found some records, so that I will return them");
			String result = retrievePhrases(sign, today);
			return result;
		}

		LOG.debug("I did not found records, so that I will generate and return them");
		generatePhrases(sign);
		String result = retrievePhrases(sign, today);
		return result;
	}

	private void generatePhrases(String sign) {
		List<Long> idsByCategoryHealth = this.forecastDao.getIdsByCategory(CATEGORY_HEALTH);
		List<Long> idsByCategoryLove = this.forecastDao.getIdsByCategory(CATEGORY_LOVE);
		List<Long> idsByCategoryMoney = this.forecastDao.getIdsByCategory(CATEGORY_MONEY);

		LOG.debug("Health: {}", idsByCategoryHealth.size());
		LOG.debug("Love: {}", idsByCategoryLove.size());
		LOG.debug("Money: {}", idsByCategoryMoney.size());

		Long randomIdHealt = idsByCategoryHealth
				.get(ThreadLocalRandom.current().nextInt(0, idsByCategoryHealth.size()));
		Long randomIdLove = idsByCategoryLove.get(ThreadLocalRandom.current().nextInt(0, idsByCategoryLove.size()));
		Long randomIdMoney = idsByCategoryMoney.get(ThreadLocalRandom.current().nextInt(0, idsByCategoryMoney.size()));

		this.forecastSignDao.generate(sign, randomIdHealt, randomIdLove, randomIdMoney);
	}

	private String retrievePhrases(String sign, Date today) {
		List<Forecast> forecasts = this.forecastSignDao.retrieveRecordsForInterval(sign, today,
				DateUtils.addDays(today, 1), HOROSCOPE_CATEGORIES);
		StringBuilder sb = new StringBuilder();
		for (Forecast forecast : forecasts) {
			String key = this.env.getProperty("horoscope.language") + ".categories." + forecast.getCategory();
			String value = this.env.getProperty(key);
			LOG.debug("KEY: [{}], VALUE: [{}]", key, value);
			sb.append(value);
			sb.append(": ");
			sb.append(forecast.getPhrase());
			sb.append(" ");
		}
		return sb.toString();
	}

	private void validateSign(String sign) {
		List<String> translatedSigns = new ArrayList<>();
		String appLanguage = this.env.getProperty("horoscope.language");
		ZODIAC_SIGNS.forEach(signx -> {
			final String key = appLanguage + ".signs." + signx;
			final String value = StringUtils.lowerCase(this.env.getProperty(key));
			LOG.debug("KEY: [{}], VALUE: [{}]", key, value);
			translatedSigns.add(value);
		});
		if (!translatedSigns.contains(StringUtils.lowerCase(sign))) {
			throw new RuntimeException("Oooooops, badabuuuum! " + sign + "|" + translatedSigns);
		}
	}

}
