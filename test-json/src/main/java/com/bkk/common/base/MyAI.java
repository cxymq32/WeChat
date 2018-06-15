package com.bkk.common.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.http.HttpException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bkk.controller.G_CenterController;

public class MyAI {
	private final static Logger log = Logger.getLogger(MyAI.class);

	public static void main(String[] args) throws Exception {
		// System.out.println(talk("滨州天气怎么样").get("data"));
		// System.out.println(talk("有什么好吃的").get("data"));
		// System.out.println(talk("15乘以63等于几", "123").get("data"));
		// System.out.println(talk("滨州天气怎么样", "123").get("data"));
	}

	public static JSONObject talk(String question, String session)
			throws UnsupportedEncodingException, HttpException, IOException {
		String url = "https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat";
		String param = "app_id=1106967014";
		param += "&nonce_str=" + UUID.randomUUID().toString().replaceAll("-", "");
		param += "&question=" + URLEncoder.encode(question, "UTF-8");
		param += "&session=" + session + "&time_stamp=" + System.currentTimeMillis() / 1000;
		String sign = MyMD5.MD5Encode(param + "&app_key=JTMq3LQfNeQQ9CDN", "UTF-8").toUpperCase();
		url += "?" + param + "&sign=" + sign;
		log.info("MyAI===========URL=========>>" + url);
		return JSON.parseObject(MyHTTP.httpGet(url));
	}

}
