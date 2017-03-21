package com.shanlin.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
	protected static final String DEFAULT_PATTERN = "yyyy-MM-dd";

	public static Date parse(String value) {
		return parse(value, "yyyy-MM-dd");
	}

	public static Date parse(String value, String pattern) {
		if (StringHelper.isEmpty(value)) {
			return null;
		}
		if (StringHelper.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		try {
			return fmt.parse(value);
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date[] parseArray(String[] values) {
		return parseArray(values, "yyyy-MM-dd");
	}

	public static Date[] parseArray(String[] values, String pattern) {
		if ((values == null) || (values.length == 0)) {
			return null;
		}
		if (StringHelper.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		Date[] dates = new Date[values.length];
		for (int i = 0; i < values.length; i++) {
			if (!StringHelper.isEmpty(values[i])) {
				try {
					dates[i] = fmt.parse(values[i]);
				} catch (ParseException e) {
				}
			}
		}
		return dates;
	}

	public static String format(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (StringHelper.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.format(date);
		} catch (IllegalArgumentException e) {
		}
		return "";
	}
	
	public static Date getBirthday(String cardID) {
		StringBuilder tempStr = new StringBuilder("");
		if (cardID != null && cardID.trim().length() > 0) {
			if (cardID.trim().length() == 15) {
				tempStr.append(cardID.substring(6, 12));
				tempStr.insert(4, '-');
				tempStr.insert(2, '-');
				tempStr.insert(0, "19");
			} else if (cardID.trim().length() == 18) {
				tempStr = new StringBuilder(cardID.substring(6, 14));
				tempStr.insert(6, '-');
				tempStr.insert(4, '-');
			}
		}
		return DateParser.parse(tempStr.toString());
	}
}
