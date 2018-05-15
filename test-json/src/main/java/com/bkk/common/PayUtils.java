package com.bkk.common;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import net.sf.json.JSONObject;


public class PayUtils {
	public static String getAccess_token(){
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx7255a01c5dfe1f7c&secret=e035d9830443adaa943e7f6415b20c21";
		String access_token_json = postWithXmlParams(tokenUrl, "");
		JSONObject json = JSONObject.fromObject(access_token_json);
		String access_token = (String) json.get("access_token");
		System.out.println("access_token===>"+access_token);
		return access_token;
	}
	/** 时间戳 */
	public static String getTimeStamp() {
		long seconds = System.currentTimeMillis() / 1000;
		return String.valueOf(seconds);
	}

	/** 解析XML */
	public static String parseXML(String xml, String key) {
		// 解析books.xml文件
		// 创建SAXReader的对象reader
		SAXReader reader = new SAXReader();
		try {
			// 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
			Document document = reader.read(new ByteArrayInputStream(xml.getBytes()));
			// 通过document对象获取根节点bookstore
			Element bookStore = document.getRootElement();
			// 通过element对象的elementIterator方法获取迭代器
			Iterator it = bookStore.elementIterator();
			// 遍历迭代器，获取根节点中的信息（书籍）
			while (it.hasNext()) {
				Element next = (Element) it.next();
				if (key.equals(next.getName())) {
					return next.getTextTrim();
				}

			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

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
	public static String postParams(String url, String params) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		try {
			httpost.setEntity(new StringEntity(params, "UTF-8"));
			httpost.setHeader("content-type","application/json");
			HttpResponse response = client.execute(httpost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * 
	 * @throws Exception
	 */
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
		sb.append("key=" + key);
		System.out.println("md5:" + sb.toString());
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String sign = MD5Utils.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		System.out.println("packge签名:" + sign);
		return sign;

	}
}
