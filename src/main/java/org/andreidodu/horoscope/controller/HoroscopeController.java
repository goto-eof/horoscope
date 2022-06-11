package org.andreidodu.horoscope.controller;

import org.andreidodu.horoscope.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/horoscope")
public class HoroscopeController {

	@Autowired
	private ForecastService forecastService;

	@GetMapping
	public ResponseEntity<String> lista() {
		return ResponseEntity.ok("lista");
	}

	@GetMapping("/sign/{sign}")
	public ResponseEntity<String> getByType(@PathVariable("sign") String sign) {
		String messaggio = "Boh, e io che ne so.";
		if ("leone".equalsIgnoreCase(sign)) {
			messaggio = this.forecastService.retrieveBySing(sign);
		}
		return ResponseEntity.ok(messaggio);
	}

}
