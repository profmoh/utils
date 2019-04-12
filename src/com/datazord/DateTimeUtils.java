package com.datazord;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

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

	public static String formateDate(Date date, String formatPattern) {
		if (StringUtils.isBlank(formatPattern))
			return date.toString();

		SimpleDateFormat formatter = new SimpleDateFormat(formatPattern);

		return formatter.format(new Date());
	}

	public static String formateLocalDateTime(LocalDateTime localDateTime, String formatPattern) {
		if (StringUtils.isBlank(formatPattern))
			return localDateTime.toString();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

		return localDateTime.format(formatter);
	}
}
