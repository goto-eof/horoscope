package org.andreidodu.horoscope.repository;

import java.util.Date;
import java.util.List;

import org.andreidodu.horoscope.entities.Forecast;

public interface ForecastSignDao {

	boolean thereAreRecordsForInterval(Date startDate, Date endDate, List<String> categories);

	public void add(String sign, Long... ids);

	List<Forecast> retrieveRecordsForInterval(Date today, Date addDays, List<String> horoscopeCategories);

}
