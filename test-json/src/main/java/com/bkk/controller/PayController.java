package com.bkk.controller;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bkk.common.Model_Pay;
import com.bkk.common.base.MyDate;
import com.bkk.common.base.MyHTTP;
import com.bkk.common.base.MyMD5;
import com.bkk.common.base.MyProperties;
import com.bkk.common.base.MyXML;

@Controller
@RequestMapping("/wx/pay")
public class PayController {
	Logger logger = Logger.getLogger(PayController.class);

	/** 获取PrepayId */
	@ResponseBody
	@RequestMapping("/getPrepay")
	public Object prepayId(Model_Pay payModel) throws Exception {
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");// 32位随机数
		String out_trade_no = System.currentTimeMillis() + "";// 商户订单号

		String appid = MyProperties.getProperties("my.properties", "x_appid");
		String key = MyProperties.getProperties("my.properties", "x_secret");
		String payNum = MyProperties.getProperties("my.properties", "x_payNum");
		String cargoDescribe = "可可订座";
		String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";// 支付接口

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);// appid
		// packageParams.put("attach", "支付测试");// 附加数据
		packageParams.put("body", cargoDescribe);// 商品描述
		// packageParams.put("detail", "text");
		packageParams.put("mch_id", payNum);// 商户号
		packageParams.put("nonce_str", nonce_str);// 随机数
		packageParams.put("notify_url", "http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");
		packageParams.put("openid", payModel.getOpenid());
		packageParams.put("out_trade_no", out_trade_no);// 商户订单号
		packageParams.put("spbill_create_ip", "14.23.150.211");// 订单生成的机器 IP
		packageParams.put("total_fee", payModel.getTotal_fee());// 总金额
		packageParams.put("trade_type", "JSAPI");
		logger.info("packageParams===========>" + packageParams.toString());
		String sign = MyMD5.createSign(packageParams, key);
		String xml = "<xml>" + //
				"   <appid>" + appid + "</appid>" +
				// " <attach>支付测试</attach>" +
				"   <body>" + cargoDescribe + "</body>" + //
				"   <mch_id>1503772501</mch_id>" +
				// " <detail>text</detail>" +
				"   <nonce_str>" + nonce_str + "</nonce_str>" + //
				"   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>" + //
				"   <openid>" + payModel.getOpenid() + "</openid>" + //
				"   <out_trade_no>" + out_trade_no + "</out_trade_no>" + //
				"   <spbill_create_ip>14.23.150.211</spbill_create_ip>" + //
				"   <total_fee>" + payModel.getTotal_fee() + "</total_fee>" + //
				"   <trade_type>JSAPI</trade_type>" + //
				"   <sign>" + sign + "</sign>" + //
				"</xml>";
		logger.info("xml=========>>" + xml);
		String responseXmlre = MyHTTP.postWithXmlParams(payUrl, xml);
		logger.info("responseXmlre=========>>" + responseXmlre);
		String prepay_id = MyXML.parseXML(responseXmlre, "prepay_id");
		logger.info("################################" + prepay_id + "################################");
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = MyDate.getTimeStamp();
		String packages = "prepay_id=" + prepay_id;
		finalpackage.put("appId", appid);
		finalpackage.put("nonceStr", nonce_str);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		finalpackage.put("timeStamp", timestamp);
		String finalsign = MyMD5.createSign(finalpackage, key);
		payModel.setFinalsign(finalsign);
		payModel.setTimestamp(timestamp);
		payModel.setPackages(packages);
		payModel.setNonceStr(nonce_str);
		return payModel;

	}
}
