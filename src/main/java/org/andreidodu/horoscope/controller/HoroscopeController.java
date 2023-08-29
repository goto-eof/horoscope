package org.andreidodu.horoscope.controller;

import lombok.RequiredArgsConstructor;
import org.andreidodu.horoscope.dto.ForecastDTO;
import org.andreidodu.horoscope.dto.SignDTO;
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

import java.util.List;

@RestController
@RequestMapping(path = "/horoscope")
@RequiredArgsConstructor
public class HoroscopeController {

    final private ForecastService forecastService;

    @GetMapping("/signs")
    public ResponseEntity<List<SignDTO>> getAllSigns() {
        return ResponseEntity.ok(this.forecastService.retrieveAllSigns());
    }

    @GetMapping("/sign/{sign}")
    public ResponseEntity<String> getBySign(@PathVariable("sign") String sign) {
        String result = this.forecastService.retrieveBySing(sign);
        return ResponseEntity.ok(result);
    }

}
