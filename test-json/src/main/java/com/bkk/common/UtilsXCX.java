package com.bkk.common;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.bkk.common.base.MyHTTP;
import com.bkk.common.base.MyProperties;
import com.bkk.controller.G_CenterController;

import net.sf.json.JSONObject;

public class UtilsXCX {
	private final static Logger log = Logger.getLogger(UtilsXCX.class);
	private static String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	private static String msgUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";

	/** 小程序获取token */
	public static String getAccess_token() {
		String appid = MyProperties.getProperties("my.properties", "x_appid");
		String secret = MyProperties.getProperties("my.properties", "x_secret");
		String url = MessageFormat.format(tokenUrl, appid, secret);
		String access_token_json = MyHTTP.postWithXmlParams(url, "");
		JSONObject json = JSONObject.fromObject(access_token_json);
		log.info("access_token_json===>" + access_token_json);
		String access_token = (String) json.get("access_token");
		log.info("access_token===>" + access_token);
		return access_token;
	}

	/**
	 * 小程序发消息 <br>
	 * {touser: opid,template_id: 'F--',page: '/pages/index/index',form_id:
	 * fId,data:{"keyword1": { "value": "ss", "color": "#4a4a4a" }}}
	 */
	public static String sengMsg(String jsonStr) throws Exception {
		log.info("packageParams===>" + jsonStr);
		String url = msgUrl + UtilsXCX.getAccess_token();
		log.info("url===>" + url);
		return MyHTTP.postParams(url, jsonStr);

	}
}
