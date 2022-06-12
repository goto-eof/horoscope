package org.andreidodu.horoscope.controller;

import org.andreidodu.horoscope.dto.ForecastDTO;
import org.andreidodu.horoscope.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/horoscope")
public class HoroscopeController {

	@Autowired
	private ForecastService forecastService;

	@GetMapping
	public ResponseEntity<String> lista() {
		// TODO
		return ResponseEntity.ok("list");
	}

	@GetMapping("/sign/{sign}")
	public ResponseEntity<String> getBySign(@PathVariable("sign") String sign) {
		String result = this.forecastService.retrieveBySing(sign);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	public ResponseEntity<ForecastDTO> publish(@RequestBody ForecastDTO forecastDto) {
		// TODO
		return ResponseEntity.ok(forecastDto);
	}

	@PutMapping("/sign/{sign}")
	public ResponseEntity<ForecastDTO> update(@PathVariable("sign") String sign, @RequestBody ForecastDTO forecastDto) {
		// TODO
		return ResponseEntity.ok(forecastDto);
	}

	@DeleteMapping("/sign/{sign}")
	public ResponseEntity<Boolean> delete(@PathVariable("sign") String sign) {
		// TODO
		return ResponseEntity.ok(Boolean.TRUE);
	}

}
