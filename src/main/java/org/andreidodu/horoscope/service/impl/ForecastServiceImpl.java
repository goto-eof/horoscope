package org.andreidodu.horoscope.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.andreidodu.horoscope.dto.ForecastByCategoryDTO;
import org.andreidodu.horoscope.dto.ForecastDTO;
import org.andreidodu.horoscope.dto.SignDTO;
import org.andreidodu.horoscope.entities.Forecast;
import org.andreidodu.horoscope.mapper.SignMapper;
import org.andreidodu.horoscope.repository.ForecastDao;
import org.andreidodu.horoscope.repository.ForecastSignDao;
import org.andreidodu.horoscope.repository.SignDao;
import org.andreidodu.horoscope.service.ForecastService;
import org.andreidodu.horoscope.util.DateUtils;
import org.andreidodu.horoscope.util.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class ForecastServiceImpl implements ForecastService {

    private static final Logger LOG = LoggerFactory.getLogger(ForecastServiceImpl.class);

    private static final String CATEGORY_HEALTH = "health";
    private static final String CATEGORY_LOVE = "love";
    private static final String CATEGORY_MONEY = "money";

    private static final List<String> HOROSCOPE_CATEGORIES = Arrays.asList(CATEGORY_HEALTH, CATEGORY_LOVE,
            CATEGORY_MONEY);

    final private ForecastDao forecastDao;
    final private SignDao signDao;
    final private ForecastSignDao forecastSignDao;
    final private SignUtils signUtil;
    final private Environment env;
    final private SignMapper signMapper;

    @Override
    public ForecastDTO retrieveBySing(String sign) {

        boolean validationResult = this.signUtil.validateSign(sign);
        if (!validationResult) {
            throw new RuntimeException("Oooops ooops opps!");
        }

        // if we have some records for today, then it means that we can retrieve and
        // return those records, else we should generate them (I will change it in the
        // future)
        Date today = DateUtils.truncateDate(new Date());
        boolean thereAre = this.forecastSignDao.thereAreRecordsForInterval(sign, today, DateUtils.addDays(today, 1),
                HOROSCOPE_CATEGORIES);

        LOG.debug("thereAre: {}", thereAre);

        if (thereAre) {
            LOG.debug("I found some records, so that I will return them");
            return retrieveForecast(sign, today);
        }

        LOG.debug("I did not found records, so that I will generate and return them");
        generatePhrases(sign);
        return retrieveForecast(sign, today);
    }

    @Override
    public List<SignDTO> retrieveAllSigns() {
        return StreamSupport
                .stream(this.signDao.findAll().spliterator(), false)
                .toList()
                .stream()
                .map(signMapper::toDTO)
                .toList();
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

        this.forecastSignDao.generate(sign, new Date(), randomIdHealt, randomIdLove, randomIdMoney);
    }

    private ForecastDTO retrieveForecast(String sign, Date today) {
        List<Forecast> forecasts = this.forecastSignDao.retrieveRecordsForInterval(sign, today,
                DateUtils.addDays(today, 1), HOROSCOPE_CATEGORIES);
        ForecastDTO forecastDTO = ForecastDTO.builder().sign(sign).build();
        forecastDTO.setForecasts(forecasts.stream().map(forecast -> ForecastByCategoryDTO.builder().category(forecast.getCategory()).rating(forecast.getRaing()).forecast(forecast.getPhrase()).build()).toList());
        return forecastDTO;
    }

}
