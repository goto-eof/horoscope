package org.andreidodu.horoscope.repository;

import org.andreidodu.horoscope.entities.Forecast;

public interface ForecastDao {

	public Long getMaxId();

	public void addForecast(Forecast forecast);

}
