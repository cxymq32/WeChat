package com.bkk.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		
		System.out.println(sf.format(new Date()));
	}
}
