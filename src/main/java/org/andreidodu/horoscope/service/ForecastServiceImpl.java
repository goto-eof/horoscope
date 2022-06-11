package org.andreidodu.horoscope.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.andreidodu.horoscope.repository.ForecastDao;
import org.andreidodu.horoscope.repository.ForecastSignDao;
import org.andreidodu.horoscope.repository.ForecastSignDaoImpl;
import org.andreidodu.horoscope.repository.SignDao;
import org.andreidodu.horoscope.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ForecastServiceImpl implements ForecastService {

	private static final Logger LOG = LoggerFactory.getLogger(ForecastServiceImpl.class);

	@Autowired
	private ForecastDao forecastDao;

	@Autowired
	private ForecastSignDao forecastSignDao;

	@Autowired
	private SignDao signDao;

	@Override
	public String retrieveBySing(String sign) {

		// TODO da ripensare
		// if we have some records for today, then it means that we can retrieve and
		// return those records, else we should generate them
		Date today = DateUtils.truncateDate(new Date());
		boolean thereAre = this.forecastSignDao.thereAreRecordsForInterval(today, DateUtils.addDays(today, 1));

		if (thereAre) {
			LOG.debug("I found some records, so that I will return them");
			// TODO recupera, componi e restituisci testo
			return "";
		}

		LOG.debug("I did not found records, so that I will generate and return them");

		Long maxId = this.forecastDao.getMaxId();
		System.out.println(maxId);
		return "maxId: " + maxId;
	}

}
