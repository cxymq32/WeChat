package com.bkk.common.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyString {
	/** 随机获取一个商家码 */
	public static String shopCode() {
		String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		List<String> list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		return sb.toString().substring(5, 9);
	}

	/** 是否为空 */
	public static boolean isEmpty(String val) {
		if (val == null || "".equals(val) || val.trim().length() < 1)
			return true;
		return false;
	}

	/** 是否不为空 */
	public static boolean isNotEmpty(String val) {
		if (val == null || "".equals(val) || val.trim().length() < 1)
			return false;
		return true;
	}
}
