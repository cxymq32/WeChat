package com.bkk.common.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MyHTTP {
	/**
	 * 发送请求
	 * 
	 * @param url
	 *            请求路径
	 * @param xmlParams
	 *            xml字符串
	 * @return
	 */
	public static String postWithXmlParams(String url, String xmlParams) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		try {
			httpost.setEntity(new StringEntity(xmlParams, "UTF-8"));
			HttpResponse response = client.execute(httpost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/** 发送json数据 */
	public static String postParams(String url, String jsonParams) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		try {
			httpost.setEntity(new StringEntity(jsonParams, "UTF-8"));
			httpost.setHeader("content-type", "application/json");
			HttpResponse response = client.execute(httpost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
