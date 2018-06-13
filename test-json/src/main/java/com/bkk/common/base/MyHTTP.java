package com.bkk.common.base;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
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

	public static String httpGet(String url, String charset) throws HttpException, IOException {

		DefaultHttpClient client = new DefaultHttpClient();
		String json = null;
		HttpGet httpGet = new HttpGet();
		// 设置参数
		try {
			httpGet.setURI(new URI(url));
		} catch (URISyntaxException e) {
			throw new HttpException("请求url格式错误。" + e.getMessage());
		}
		// 发送请求
		HttpResponse httpResponse = client.execute(httpGet);
		// 获取返回的数据
		HttpEntity entity = httpResponse.getEntity();
		byte[] body = EntityUtils.toByteArray(entity);
		StatusLine sL = httpResponse.getStatusLine();
		int statusCode = sL.getStatusCode();
		if (statusCode == 200) {
			json = new String(body, charset);
			entity.consumeContent();
		} else {
			throw new HttpException("statusCode=" + statusCode);
		}
		return json;
	}
}
