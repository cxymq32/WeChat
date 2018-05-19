package com.bkk.controller;

import java.text.SimpleDateFormat;

import com.bkk.common.PayUtils;

import net.sf.json.JSONObject;

public class test {
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	// 菜单创建（POST） 限100（次/天）
	public static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb90a701330e3bab8&secret=69063f87f30a72feefdbf197909b7868";

	public static void main(String[] args) {
		String a = PayUtils.postParams(access_token_url, "");
		JSONObject json = JSONObject.fromObject(a);
		String access_token = (String) json.get("access_token");
		System.out.println(access_token);
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", access_token);
		String menu = "{" + "     \"button\":[" + "     {    " + "          \"type\":\"click\","
				+ "          \"name\":\"今日歌曲\"," + "          \"key\":\"V1001_TODAY_MUSIC\"" + "      }," + "      {"
				+ "           \"name\":\"菜单\"," + "           \"sub_button\":[" + "           {    "
				+ "               \"type\":\"view\"," + "               \"name\":\"搜索\","
				+ "               \"url\":\"http://www.soso.com/\"" + "            }," + "            {"
				+ "               \"type\":\"view\"," + "               \"name\":\"视频\","
				+ "               \"url\":\"http://v.qq.com/\"" + "            }," + "            {"
				+ "               \"type\":\"click\"," + "               \"name\":\"赞一下我们\","
				+ "               \"key\":\"V1001_GOOD\"" + "            }]" + "       }]" + " }";
		String jsonObject = PayUtils.postParams(url, menu);
		System.out.println("jsonObject====>>" + jsonObject);
	}
}
