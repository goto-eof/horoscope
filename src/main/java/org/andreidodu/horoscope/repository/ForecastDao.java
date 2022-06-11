package org.andreidodu.horoscope.repository;

import java.util.List;

import org.andreidodu.horoscope.entities.Forecast;

public interface ForecastDao {

	public Long getMaxId();

	public void addForecast(Forecast forecast);

	public List<Long> getIdsByCategory(String categoryHealth);

}
