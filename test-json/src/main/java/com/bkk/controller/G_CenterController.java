package com.bkk.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bkk.common.GZHUtils;
import com.bkk.common.PayUtils;

import net.sf.json.JSONObject;

/**
 * 中央处理器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/centercontroller")
public class G_CenterController {
	private final static Logger log = Logger.getLogger(G_CenterController.class);
	// 菜单创建（POST） 限100（次/天）
	public static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb90a701330e3bab8&secret=69063f87f30a72feefdbf197909b7868";

	@ResponseBody
	@RequestMapping(value = "/startService", method = RequestMethod.GET)
	public void autoResponse(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr, HttpServletResponse response)
			throws Exception {
		log.info("收到微信get验证: signature" + signature + "\ttimestamp:" + timestamp + "\tnonce:" + nonce + "\techostr:"
				+ echostr);
		if (GZHUtils.checkSignature(signature, timestamp, nonce)) {
			log.info("check success!");
			response.getOutputStream().println(echostr);
		}
	}


}