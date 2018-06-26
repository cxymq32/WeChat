package com.bkk.common.base;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
		CloseableHttpClient client = HttpClients.createDefault();
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
		CloseableHttpClient client = HttpClients.createDefault();
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

	public static String getImg(String url, String jsonParams) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpost = new HttpPost(url);
		try {
			httpost.setEntity(new StringEntity(jsonParams, "UTF-8"));
			httpost.setHeader("content-type", "application/json");
			HttpResponse response = client.execute(httpost);
		      
            InputStream in = response.getEntity().getContent();  
  
            File file=new File("d:\\a.jpg");//可以是任何图片格式.jpg,.png等  
            FileOutputStream fos=new FileOutputStream(file);  
                
            byte[] b = new byte[1024];  
            int nRead = 0;  
            while ((nRead = in.read(b)) != -1) {  
                fos.write(b, 0, nRead);  
            }  
            fos.flush();  
            fos.close();  
            in.close();  
			
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String httpGet(String url) throws HttpException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
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
			json = new String(body, "UTF-8");
			EntityUtils.consume(entity);
		} else {
			throw new HttpException("statusCode=" + statusCode);
		}
		return json;
	}

	public static void main(String[] args) {
		String a = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx7255a01c5dfe1f7c&secret=e035d9830443adaa943e7f6415b20c21";
		System.out.println(sendPost(a, ""));

	}

	public static String sendPost(String url, String data) {
		String response = null;
		try {
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse httpresponse = null;
			try {
				httpclient = HttpClients.createDefault();
				HttpPost httppost = new HttpPost(url);
				StringEntity stringentity = new StringEntity(data, ContentType.create("text/json", "UTF-8"));
				httppost.setEntity(stringentity);
				httpresponse = httpclient.execute(httppost);
				response = EntityUtils.toString(httpresponse.getEntity());
			} finally {
				if (httpclient != null) {
					httpclient.close();
				}
				if (httpresponse != null) {
					httpresponse.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
