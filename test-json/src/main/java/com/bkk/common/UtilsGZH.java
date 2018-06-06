package com.bkk.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSON;
import com.bkk.common.base.MyHTTP;
import com.bkk.common.base.MyProperties;
import com.bkk.common.base.MyRedis;
import com.bkk.common.base.SHA1;

import net.sf.json.JSONObject;

public class UtilsGZH {
	private static final Logger log = Logger.getLogger(UtilsGZH.class);
	// 菜单创建（POST） 限100（次/天）
	public static String create_menu_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";
	// 获取access_token的接口地址（GET） 限200（次/天）
	public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	// 获取openid
	public static String openid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";

	public static void main(String[] args) {
	}

	/**
	 * 获取网页版js调用权限
	 * 
	 * @param urlStr
	 */
	public static Model getJSapi(Model model, String urlStr) {
		String appid = MyProperties.getProperties("my.properties", "g_appid");
		long timestamp = new Date().getTime();
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");// 32位随机数

		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + UtilsGZH.getAccessToken()
				+ "&type=jsapi";
		com.alibaba.fastjson.JSONObject json = JSON.parseObject(MyHTTP.postParams(url, ""));
		log.info("json===========>" + json);
		String ticket = (String) json.get("ticket");
		log.info("ticket===========>" + ticket);

		// 生成signature
		List<String> nameList = new ArrayList<String>();
		nameList.add("noncestr");
		nameList.add("timestamp");
		nameList.add("url");
		nameList.add("jsapi_ticket");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("noncestr", nonceStr);
		valueMap.put("timestamp", timestamp);
		valueMap.put("url", urlStr);
		valueMap.put("jsapi_ticket", ticket);
		Collections.sort(nameList);
		String origin = "";
		for (int i = 0; i < nameList.size(); i++) {
			origin += nameList.get(i) + "=" + valueMap.get(nameList.get(i)).toString() + "&";
		}
		origin = origin.substring(0, origin.length() - 1);
		log.info("origin=============>" + origin);
		String signature = SHA1.encode(origin).toLowerCase();
		log.info("signature=============>" + signature);

		model.addAttribute("appId", appid);
		model.addAttribute("timestamp", timestamp);
		model.addAttribute("nonceStr", nonceStr);
		model.addAttribute("signature", signature);
		return model;
	}

	/** 用户发给公众号的消息-保存为josn文本格式 */
	public static void reciveMsg(String jsonContent) throws Exception {
		FileWriter fw = null;
		String path = MyProperties.getProperties("my.properties", "g_msgFile");
		// 如果文件存在，则追加内容；如果文件不存在，则创建文件
		File p = new File(path);
		if (!p.exists()) {
			p.mkdirs();
		}
		File f = new File(path + "msg.txt");
		fw = new FileWriter(f, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(jsonContent);
		pw.flush();
		fw.flush();
		pw.close();
		fw.close();
	}

	/** 用户下单后通过公众号号给商户发消息 */
	public static String sendMsg(String jsonContent) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + getAccessToken();
		return MyHTTP.postParams(url, jsonContent);
	}

	/** 获取用户信息,参数openid返回json对象 */
	public static com.alibaba.fastjson.JSONObject getUserInfo(String openid) throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + getAccessToken() + "&openid="
				+ openid;
		String userInfo = MyHTTP.postParams(url, "");
		log.info("userInfo=======>>" + userInfo);
		com.alibaba.fastjson.JSONObject userInfoJson = JSON.parseObject(userInfo);
		return userInfoJson;

	}

	/** 创建菜单 */
	public static String createMenu() throws Exception {
		String access_token = getAccessToken();
		String url = MessageFormat.format(create_menu_url, access_token);
		String jsonObject = MyHTTP.postParams(url, readFile("menu"));
		log.info("jsonObject====>>" + jsonObject);
		return jsonObject;
	}

	/** 微信网页授权snsapi_base,snsapi_userinfo */
	public static String getUserOpenId(String code) {
		String appid = MyProperties.getProperties("my.properties", "g_appid");
		String secret = MyProperties.getProperties("my.properties", "g_appSecret");
		String url = MessageFormat.format(openid_url, appid, secret, code);
		log.info("getUserOpenId====url=========>>" + url);
		return MyHTTP.postParams(url, "");
	}

	/** 获取access_token */
	public static String getAccessToken() {
		String token = MyRedis.get("token");
		if (token != null) {
			log.info("redisCcache===>>" + token);
			return token;
		} else {
			String appid = MyProperties.getProperties("my.properties", "g_appid");
			String secret = MyProperties.getProperties("my.properties", "g_appSecret");
			String url = MessageFormat.format(access_token_url, appid, secret);
			log.info("access_token_url===>>" + url);
			String access_token_json = MyHTTP.postParams(url, "");
			JSONObject json = JSONObject.fromObject(access_token_json);
			String access_token = (String) json.get("access_token");
			log.info("access_token===>" + access_token);
			MyRedis.set("token", access_token, 7000);// 设置7000秒过期,微信服务器是7200秒过期
			return access_token;
		}
	}

	/** 读取文件 */
	public static String readFile(String fileName) throws Exception {
		MyProperties temp = new MyProperties();
		String propertiesDir = temp.getClass().getClassLoader().getResource("").getPath() + fileName;
		String encoding = "UTF-8";
		File file = new File(propertiesDir);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		FileInputStream in = new FileInputStream(file);
		in.read(filecontent);
		in.close();
		return new String(filecontent, encoding);
	}

	/** 验证签名 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) throws Exception {
		String token = MyProperties.getProperties("my.properties", "g_token");
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		md = MessageDigest.getInstance("SHA-1");
		// 将三个参数字符串拼接成一个字符串进行sha1加密
		byte[] digest = md.digest(content.toString().getBytes());
		tmpStr = byteToStr(digest);

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/** 将字节数组转换为十六进制字符串 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/** 将字节转换为十六进制字符串 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}
