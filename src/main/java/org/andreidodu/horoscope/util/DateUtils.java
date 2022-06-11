package org.andreidodu.horoscope.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date truncateDate(Date inDate) {
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(inDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return new Date(cal.getTimeInMillis());
	}

}
