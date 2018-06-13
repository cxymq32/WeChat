package com.bkk.controller;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.bkk.common.base.MyHTTP;
import com.bkk.common.base.MyMD5;

public class TestAI {
	public static void main(String[] args) throws Exception {
		String url = "https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat";
		SortedMap<String, String> jsonContent = new TreeMap<String, String>();
		jsonContent.put("app_id", "1106967014");
		jsonContent.put("nonce_str", "fa577ce340859f9fe");
		jsonContent.put("question", "aaa");
		jsonContent.put("session", "10000");
		jsonContent.put("time_stamp", (System.currentTimeMillis() / 1000) + "");
		jsonContent.put("sign", createSign(jsonContent, "JTMq3LQfNeQQ9CDN"));
		System.out.println(jsonContent.toString());
		MyHTTP.postParams(url, jsonContent.toString());
	}

	/** 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。 */
	public static String createSign(SortedMap<String, String> packageParams, String key) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("app_key=" + key);
		System.out.println("md5:" + sb.toString());
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String sign = MyMD5.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		System.out.println("packge签名:" + sign);
		return sign;

	}
}
