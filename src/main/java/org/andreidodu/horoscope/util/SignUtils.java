package org.andreidodu.horoscope.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SignUtils {

	private static final Logger LOG = LoggerFactory.getLogger(SignUtils.class);

	private static final List<String> ZODIAC_SIGNS = Arrays.asList("aries", "taurus", "gemini", "cancer", "leo", "virgo", "libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces");

	@Autowired
	private Environment env;

	public boolean validateSign(String sign) {
		List<String> translatedSigns = new ArrayList<>();
		String appLanguage = this.env.getProperty("horoscope.language");
		ZODIAC_SIGNS.forEach(signx -> {
			final String key = appLanguage + ".signs." + signx;
			final String value = StringUtils.lowerCase(this.env.getProperty(key));
			LOG.debug("KEY: [{}], VALUE: [{}]", key, value);
			translatedSigns.add(value);
		});
		if (!translatedSigns.contains(StringUtils.lowerCase(sign))) {
			LOG.debug("Oooooops, badabuuuum! (" + sign + " not in " + translatedSigns + ")");
			return false;
		}
		LOG.debug("{} is a valid sign");
		return true;
	}

}
