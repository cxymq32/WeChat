package com.bkk.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bkk.common.GZHUtils;
import com.bkk.common.PayUtils;

public class test {
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception {
//		String a = "{\"subscribe\":1,\"openid\":\"oAuZ-wJBtegn6Nw00Y5hrH7JhEdI\",\"nickname\":\"勿忘心安灬\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"约旦\",\"headimgurl\":\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/ceTMo16gvia6K09rcCSfc6KO6tunkdGglIgwQibUCFjBXXMicM829iafUAwv61R4eyic38Scicxv2Uljhg2gt1J3cVmLHeCnPN6sRf\\/132\",\"subscribe_time\":1527239263,\"remark\":\"\",\"groupid\":0,\"tagid_list\":[],\"subscribe_scene\":\"ADD_SCENE_QR_CODE\",\"qr_scene\":0,\"qr_scene_str\":\"\"}";
//		String a = "<xml>" + "  <ToUserName><![CDATA[oAuZ-wJBtegn6Nw00Y5hrH7JhEdI]]></ToUserName>"
//				+ "  <FromUserName><![CDATA[gh_a43d2eeb4e51]]></FromUserName>"
//				+ "  <CreateTime><![CDATA[1527054017789]]></CreateTime>" + "  <MsgType><![CDATA[text]]></MsgType>"
//				+ "  <content><![CDATA[Hi，你发的消息是：ppp]]></content>" + "</xml>";
//		System.out.println(a.indexOf("CreateTime"));
//		System.out.println(a.substring(0,a.indexOf("<CreateTime><![CDATA[")));
//		System.out.println(a.substring(a.indexOf("</CreateTime>")));
//		
//		String msg = a.substring(0,a.indexOf("<CreateTime><![CDATA["))+"<CreateTime>"+new Date().getTime()+a.substring(a.indexOf("</CreateTime>"));
//		System.out.println(msg);
		 System.out.println(GZHUtils.createMenu());
//		 System.out.println(GZHUtils.getAccessToken());
		// String t = GZHUtils.getAccessToken();
		// System.out.println(t);
		// send(t);
	}

	private static void send(String t) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + t;
		String content = "{" + "    \"touser\": \"oAuZ-wJBtegn6Nw00Y5hrH7JhEdI\", " + "    \"msgtype\": \"text\", "
				+ "    \"text\": {" + "        \"content\": \"ss\\nss<a herf='baidu.com'>test</a>ssss\"" + "    }"
				+ "}";
		String a = PayUtils.postParams(url, content);
		System.out.println(a);
	}
}
