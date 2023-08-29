package org.andreidodu.horoscope.service;

import org.andreidodu.horoscope.dto.SignDTO;
import org.andreidodu.horoscope.entities.Sign;

import java.util.List;

public interface ForecastService {

	public String retrieveBySing(String sign);

	public List<SignDTO> retrieveAllSigns();
	
}
