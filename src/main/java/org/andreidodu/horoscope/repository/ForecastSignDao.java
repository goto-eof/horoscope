package org.andreidodu.horoscope.repository;

import java.util.Date;

public interface ForecastSignDao {

	boolean thereAreRecordsForInterval(Date startDate, Date endDate);

}
