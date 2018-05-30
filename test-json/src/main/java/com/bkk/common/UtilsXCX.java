package com.bkk.common;

import com.bkk.common.base.MyHTTP;
import com.bkk.common.base.MyProperties;

import net.sf.json.JSONObject;

public class UtilsXCX {
	/** 小程序获取token */
	public static String getAccess_token() {
		String appid = MyProperties.getProperties("my.properties", "x_appid");
		String secret = MyProperties.getProperties("my.properties", "x_secret");
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
		tokenUrl = tokenUrl.replace("APPID", appid).replace("SECRET", secret);
		String access_token_json = MyHTTP.postWithXmlParams(tokenUrl, "");
		JSONObject json = JSONObject.fromObject(access_token_json);
		String access_token = (String) json.get("access_token");
		System.out.println("access_token===>" + access_token);
		return access_token;
	}
}
