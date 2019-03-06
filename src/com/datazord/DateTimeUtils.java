package com.datazord;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

	public static long timeDifferenceInMillis(Date firstDate, Date secondDate) {

		LocalDateTime firstDateTime = convertDateToLocalDateTime(firstDate);
		LocalDateTime secondDateTime = convertDateToLocalDateTime(secondDate);

		Duration duration = Duration.between(firstDateTime, secondDateTime);

//		return duration.toMillis();
		return Math.abs(duration.toMillis());
	}

	public static LocalDateTime convertDateToLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
}
