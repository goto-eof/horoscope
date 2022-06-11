package org.andreidodu.horoscope.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

import org.andreidodu.horoscope.entities.Forecast;
import org.andreidodu.horoscope.repository.ForecastDao;
import org.andreidodu.horoscope.repository.ForecastSignDao;
import org.andreidodu.horoscope.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

@Service
@Transactional
public class ForecastServiceImpl implements ForecastService {

	private static final Logger LOG = LoggerFactory.getLogger(ForecastServiceImpl.class);

	private static final String CATEGORY_HEALTH = "health";
	private static final String CATEGORY_LOVE = "love";
	private static final String CATEGORY_MONEY = "money";

	private static final List<String> HOROSCOPE_CATEGORIES = Arrays.asList(CATEGORY_HEALTH, CATEGORY_LOVE, CATEGORY_MONEY);
	private static final List<String> ZODIAC_SIGNS = Arrays.asList("aries", "taurus", "gemini", "cancer", "leo", "virgo", "libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces");

	@Autowired
	private ForecastDao forecastDao;

	@Autowired
	private ForecastSignDao forecastSignDao;

	@Autowired
	private Environment env;

	@Override
	public String retrieveBySing(String sign) {

		this.validateSign(sign);

		// -------------------------[INIZIO CANCELLAMI]------------------------------
		// TODO da ripensare
		// funzionamento attuale: il primo utente che arriva genera il nuovo oroscopo
		// della giornata per il segno in input, altrimenti lo recupera e lo restituisce
		// ad alexa
		// -------------------------[FINE CANCELLAMI]--------------------------------

		// if we have some records for today, then it means that we can retrieve and
		// return those records, else we should generate them (I will change it in the
		// future)
		Date today = DateUtils.truncateDate(new Date());
		boolean thereAre1 = this.forecastSignDao.thereAreRecordsForInterval(today, DateUtils.addDays(today, 1), new ArrayList<>());
		boolean thereAre2 = this.forecastSignDao.thereAreRecordsForInterval(today, DateUtils.addDays(today, 1), HOROSCOPE_CATEGORIES);

		LOG.debug("thereAre1: {}, thereAre2: {}", thereAre1, thereAre2);

		if (thereAre1) {
			LOG.debug("I found some records, so that I will return them");
			String result = retrievePhrases(today);
			return result;
		}

		LOG.debug("I did not found records, so that I will generate and return them");

		List<Long> idsByCategoryHealth = this.forecastDao.getIdsByCategory(CATEGORY_HEALTH);
		List<Long> idsByCategoryLove = this.forecastDao.getIdsByCategory(CATEGORY_LOVE);
		List<Long> idsByCategoryMoney = this.forecastDao.getIdsByCategory(CATEGORY_MONEY);

		LOG.debug("Health: {}", idsByCategoryHealth);
		LOG.debug("Love: {}", idsByCategoryLove);
		LOG.debug("Money: {}", idsByCategoryMoney);

		Long randomIdHealt = idsByCategoryHealth.get(ThreadLocalRandom.current().nextInt(0, idsByCategoryHealth.size()));
		Long randomIdLove = idsByCategoryLove.get(ThreadLocalRandom.current().nextInt(0, idsByCategoryLove.size()));
		Long randomIdMoney = idsByCategoryMoney.get(ThreadLocalRandom.current().nextInt(0, idsByCategoryMoney.size()));

		this.forecastSignDao.add(sign, randomIdHealt, randomIdLove, randomIdMoney);

		String result = retrievePhrases(today);

		return result;
	}

	private String retrievePhrases(Date today) {
		List<Forecast> forecasts = this.forecastSignDao.retrieveRecordsForInterval(today, DateUtils.addDays(today, 1), HOROSCOPE_CATEGORIES);
		StringBuilder sb = new StringBuilder();
		for (Forecast forecast : forecasts) {
			String key = this.env.getProperty("language") + ".categories." + forecast.getCategory();
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
		String appLanguage = this.env.getProperty("language");
		ZODIAC_SIGNS.forEach(signx -> {
			translatedSigns.add(StringUtils.lowerCase(this.env.getProperty(appLanguage + ".signs." + signx)));
		});
		if (!translatedSigns.contains(StringUtils.lowerCase(sign))) {
			throw new RuntimeException("Oooooops, badabuuuum!");
		}
	}

}
