package org.andreidodu.horoscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({@PropertySource("settings.properties"), @PropertySource("translate.properties")})
public class HoroscopeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoroscopeApplication.class, args);
	}

}
