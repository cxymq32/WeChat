package com.bkk.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bkk.common.base.MyHTTP;

public class test {
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception {
		int a ;
//		System.out.println(a > 0);
//		String a = "/usr/local/src/tomcat-80/webapps/test-json/WEB-INF/classes/";
//		System.out.println(a.substring(0, a.indexOf("/WEB-INF")));
		// resources
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));
		// System.out.println("当前的时间：" + df.format(new Date())); System.out.println();
		//
		// String jsonStr =
		// "{\"touser\":\"otnGe4uyCaDFPj5v5ek-PuasF9gI\",\"template_id\":\"Djsyb6FjccRPJ2uxWXcpdr8s5-K7YqSLiGvwwtSEXHY\",\"form_id\":\"1527674011716\",\"data\":{\"keyword1\":{\"value\":\"金汉森南美烤肉自助\",\"color\":\"#4a4a4a\"},\"keyword2\":{\"value\":\"29\",\"color\":\"#4a4a4a\"},\"keyword3\":{\"value\":\"15355355588\",\"color\":\"#4a4a4a\"},\"keyword4\":{\"value\":\"222e\",\"color\":\"#4a4a4a\"}}}";
		// System.out.println(UtilsXCX.sengMsg(jsonStr));
		// String a = "ssssssssss{0}ddddddd";
		// System.out.println(MessageFormat.format(a,"333"));
		// String a =
		// "{\"subscribe\":1,\"openid\":\"oAuZ-wJBtegn6Nw00Y5hrH7JhEdI\",\"nickname\":\"勿忘心安灬\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"约旦\",\"headimgurl\":\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/ceTMo16gvia6K09rcCSfc6KO6tunkdGglIgwQibUCFjBXXMicM829iafUAwv61R4eyic38Scicxv2Uljhg2gt1J3cVmLHeCnPN6sRf\\/132\",\"subscribe_time\":1527239263,\"remark\":\"\",\"groupid\":0,\"tagid_list\":[],\"subscribe_scene\":\"ADD_SCENE_QR_CODE\",\"qr_scene\":0,\"qr_scene_str\":\"\"}";
		// String a = "<xml>" + "
		// <ToUserName><![CDATA[oAuZ-wJBtegn6Nw00Y5hrH7JhEdI]]></ToUserName>"
		// + " <FromUserName><![CDATA[gh_a43d2eeb4e51]]></FromUserName>"
		// + " <CreateTime><![CDATA[1527054017789]]></CreateTime>" + "
		// <MsgType><![CDATA[text]]></MsgType>"
		// + " <content><![CDATA[Hi，你发的消息是：ppp]]></content>" + "</xml>";
		// System.out.println(a.indexOf("CreateTime"));
		// System.out.println(a.substring(0,a.indexOf("<CreateTime><![CDATA[")));
		// System.out.println(a.substring(a.indexOf("</CreateTime>")));
		//
		// String msg =
		// a.substring(0,a.indexOf("<CreateTime><![CDATA["))+"<CreateTime>"+new
		// Date().getTime()+a.substring(a.indexOf("</CreateTime>"));
		// System.out.println(msg);
		// System.out.println(UtilsGZH.createMenu());
		// System.out.println(GZHUtils.getAccessToken());
		// String t = GZHUtils.getAccessToken();
		// System.out.println(t);
		// send(t);
	}

	private static void send(String t) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + t;
		String content = "{" + "    \"touser\": \"oAuZ-wJBtegn6Nw00Y5hrH7JhEdI\", " + "    \"msgtype\": \"text\", "
				+ "    \"text\": {" + "        \"content\": \"ss\\nss<a herf='baidu.com'>test</a>ssss\"" + "    }"
				+ "}";
		String a = MyHTTP.postParams(url, content);
		System.out.println(a);
	}
}
