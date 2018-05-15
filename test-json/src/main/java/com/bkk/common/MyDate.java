package com.bkk.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDate {

	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");

	public static String getNextDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
		return sf.format(c.getTime());
	}

	public static String getBeforDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
		return sf.format(c.getTime());
	}
}
