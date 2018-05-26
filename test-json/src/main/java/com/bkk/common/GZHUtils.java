package com.bkk.common;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import net.sf.ehcache.CacheManager;
import net.sf.json.JSONObject;

public class GZHUtils {
	private static final Logger log = Logger.getLogger(GZHUtils.class);
	// 菜单创建（POST） 限100（次/天）
	public static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 获取access_token的接口地址（GET） 限200（次/天）
	public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";

	public static void main(String[] args) {
	}

	public static String sendMsg(String jsonContent) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + getAccessToken();
		return PayUtils.postParams(url, jsonContent);
	}

	/** 获取用户信息,参数openid返回json对象 */
	public static com.alibaba.fastjson.JSONObject getUserInfo(String openid) throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + getAccessToken() + "&openid="
				+ openid;
		String userInfo = PayUtils.postParams(url, "");
		log.info("userInfo=======>>" + userInfo);
		com.alibaba.fastjson.JSONObject userInfoJson = JSON.parseObject(userInfo);
		return userInfoJson;

	}

	/** 创建菜单 */
	public static String createMenu() throws Exception {
		String access_token = getAccessToken();
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", access_token);
		String jsonObject = PayUtils.postParams(url, readFile("menu"));
		log.info("jsonObject====>>" + jsonObject);
		return jsonObject;
	}

	/** 获取access_token */
	public static String getAccessToken() {
		String token = RedisUtil.get("token");
		if (token != null) {
			log.info("redisCcache===>>" + token);
			return token;
		} else {
			String appid = PropertiesUtil.getProperties("my.properties", "g_appid");
			String secret = PropertiesUtil.getProperties("my.properties", "g_appSecret");
			access_token_url = access_token_url.replace("APPID", appid).replace("SECRET", secret);
			log.info("access_token_url===>>" + access_token_url);

			String access_token_json = PayUtils.postParams(access_token_url, "");
			JSONObject json = JSONObject.fromObject(access_token_json);
			String access_token = (String) json.get("access_token");
			log.info("access_token===>" + access_token);
			RedisUtil.set("token", access_token, 7000);// 7000秒过期
			return access_token;
		}
	}

	/** 读取文件 */
	public static String readFile(String fileName) throws Exception {
		PropertiesUtil temp = new PropertiesUtil();
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
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String token = PropertiesUtil.getProperties("my.properties", "token");
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}
