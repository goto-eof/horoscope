package org.andreidodu.horoscope.repository;

import java.util.Date;
import java.util.List;

import org.andreidodu.horoscope.entities.Forecast;

public interface ForecastSignDao {

	boolean thereAreRecordsForInterval(String sign, Date startDate, Date endDate, List<String> categories);

	public void add(String sign, Long... ids);

	List<Forecast> retrieveRecordsForInterval(String sign, Date dateStart, Date dateEnd, List<String> horoscopeCategories);

}
