package com.bkk.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bkk.common.Model_Msg;
import com.bkk.common.PayUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wx/msg")
public class MsgController {
	Logger logger = Logger.getLogger(MsgController.class);

	/** 获取PrepayId */
	@ResponseBody
	@RequestMapping("/sengToMySelf")
	public Object sengToMySelf(Model_Msg msgModel) throws Exception {
		logger.info("openid=" + msgModel.getOpenid());
		logger.info("formid=" + msgModel.getFormid());
		logger.info("data=" + msgModel.getData());
		JSONObject packageParams = new JSONObject();
		String msgUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="
				+ PayUtils.getAccess_token();
		
		

		packageParams.put("touser", msgModel.getOpenid());
		packageParams.put("template_id", "F--MGLm3i0isE4bOHfB-4UjxechDGMQDWYxU2CDW_F8");
		packageParams.put("form_id", msgModel.getFormid());
		packageParams.put("data", msgModel.getData());

//		logger.info("msgUrl===>" + msgUrl);
//		logger.info("packageParams===>" + packageParams.toString());
//		String send = PayUtils.postParams(msgUrl, packageParams.toString());
//		logger.info("sendBack===>" + send);

		String openid = "otnGe4jjWOXIqzlJRr--oB-NnnNM";
		packageParams.put("touser", openid);
		logger.info("packageParams===>" + packageParams.toString());
		String send = PayUtils.postParams(msgUrl, packageParams.toString());
		return send;

	}
}
