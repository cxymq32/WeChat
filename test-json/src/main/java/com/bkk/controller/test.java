package com.bkk.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.bkk.common.UtilsXCX;
import com.bkk.common.base.MyHTTP;

public class test {
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception {
		String u = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + UtilsXCX.getAccess_token();
		String jsonParams = "{\"path\":\"pages/shopdetail/shopdetail?shopId=6\", \"width\": 430}";
		u = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + UtilsXCX.getAccess_token();
		jsonParams = "{\"page\":\"pages/shopdetail/shopdetail\", \"scene\": \"shopId=6\"}";
		
		System.out.println(MyHTTP.getImg(u, jsonParams,6,"d:/a.jpg"));;
		
	}

}
