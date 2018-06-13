package com.bkk.common.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MyAI {
	public static void main(String[] args) throws Exception {
//		System.out.println(talk("滨州天气怎么样").get("data"));
//		System.out.println(talk("有什么好吃的").get("data"));
		System.out.println(talk("15乘以13等于几").get("data"));
	}

	public static JSONObject talk(String question) throws UnsupportedEncodingException, HttpException, IOException {
		String url = "https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat";
		String param = "app_id=1106967014&nonce_str=fa577ce340859f9fe&question=" + URLEncoder.encode(question, "UTF-8")
				+ "&session=10000&time_stamp=" + System.currentTimeMillis() / 1000;
		String sign = MyMD5.MD5Encode(param + "&app_key=JTMq3LQfNeQQ9CDN", "UTF-8").toUpperCase();
		url = url + "?" + param + "&sign=" + sign;
		return JSON.parseObject(MyHTTP.httpGet(url, "UTF-8"));
	}

}
