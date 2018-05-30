package com.bkk.common.base;

public class MyString {

	public static boolean isEmpty(String val) {
		if (val == null || "".equals(val) || val.trim().length() < 1)
			return true;
		return false;
	}

	public static boolean isNotEmpty(String val) {
		if (val == null || "".equals(val) || val.trim().length() < 1)
			return false;
		return true;
	}
}
