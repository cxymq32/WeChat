package com.bkk.common.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDate {

	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");

	/** 支付的时候时间戳 */
	public static String getTimeStamp() {
		long seconds = System.currentTimeMillis() / 1000;
		return String.valueOf(seconds);
	}

	/** 获取下一天 */
	public static String getNextDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
		return sf.format(c.getTime());
	}

	/** 获取前一天 */
	public static String getBeforDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
		return sf.format(c.getTime());
	}
}