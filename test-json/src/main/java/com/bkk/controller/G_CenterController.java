package com.bkk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.bkk.common.GZHUtils;
import com.bkk.common.G_MessageUtil;
import com.bkk.common.PayUtils;
import com.bkk.common.PropertiesUtil;
import com.bkk.common.SHA1;
import com.bkk.common.msg.TextMessage;
import com.bkk.domain.Order;
import com.bkk.domain.User;

import net.sf.json.JSONObject;

/**
 * 中央处理器
 * 
 */
@Controller
@RequestMapping("/centercontroller")
public class G_CenterController extends BaseController {
	private final static Logger log = Logger.getLogger(G_CenterController.class);

	/** 公众号网页开发 */
	@RequestMapping("/testJS")
	public String testJS(Model model, HttpServletRequest request, HttpServletResponse response) {
		String appid = PropertiesUtil.getProperties("my.properties", "g_appid");
		long timestamp = new Date().getTime();
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");// 32位随机数

		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + GZHUtils.getAccessToken()
				+ "&type=jsapi";
		com.alibaba.fastjson.JSONObject json = JSON.parseObject(PayUtils.postParams(url, ""));
		String ticket = (String) json.get("ticket");

		// 生成signature
		List<String> nameList = new ArrayList<String>();
		nameList.add("noncestr");
		nameList.add("timestamp");
		nameList.add("url");
		nameList.add("jsapi_ticket");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("noncestr", nonceStr);
		valueMap.put("timestamp", timestamp);
		valueMap.put("url", "http://140.143.158.187/test-json/centercontroller/testJS");
		valueMap.put("jsapi_ticket", ticket);
		Collections.sort(nameList);
		String origin = "";
		for (int i = 0; i < nameList.size(); i++) {
			origin += nameList.get(i) + "=" + valueMap.get(nameList.get(i)).toString() + "&";
		}
		origin = origin.substring(0, origin.length() - 1);
		String signature = SHA1.encode(origin).toLowerCase();

		model.addAttribute("appId", appid);
		model.addAttribute("timestamp", timestamp);
		model.addAttribute("nonceStr", nonceStr);
		model.addAttribute("signature", signature);
		return "gzh/testJS";
	}

	/** 预约列表 */
	@RequestMapping("/test")
	public String test(Model model, String code, String state, HttpSession session) {
		log.info("===>>WX回调带回code");
		log.info(code + "============##########=============" + state);
		String openid = GZHUtils.getUserOpenId(code);
		log.info("openid=======>>" + openid);
		User u = userService.findByOpenid(openid);
		log.info("findUser===shopid===>>" + u.getShopId());
		List<Order> listOrder = orderService.getByShopId(u.getShopId());
		log.info("listSize===>>" + listOrder.size());
		model.addAttribute("listOrder", listOrder);
		return "gzh/test";
	}

	@RequestMapping("/createMenu")
	public void createMenu(Model model, String code, HttpSession session) throws Exception {
		if (code.equals(PropertiesUtil.getProperties("my.properties", "g_appid"))) {
			log.info("#############Menu Initialize###############");
			GZHUtils.createMenu();
			log.info("#############Menu Initialize END###############");
		}
	}

	@RequestMapping(value = "/startService", method = RequestMethod.GET)
	public void doGet(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr, HttpServletResponse response)
			throws Exception {
		log.info("收到微信get验证: signature" + signature + "\ttimestamp:" + timestamp + "\tnonce:" + nonce + "\techostr:"
				+ echostr);
		if (GZHUtils.checkSignature(signature, timestamp, nonce)) {
			log.info("check success!");
			response.getOutputStream().print(echostr);
		}
	}

	@RequestMapping(value = "/startService", method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 消息的接受、处理、响应
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/xml");
		// 调用核心业务类型接受消息、处理消息

		log.info("request.getRequestURI()====>>" + request.getRequestURI());
		String respMessage = null;
		// 默认返回的文本消息内容
		String respContent = "请求处理异常，请稍后尝试！";
		try {
			// xml请求解析
			Map<String, String> requestMap = G_MessageUtil.pareXml(request);
			JSONObject jsonObject = JSONObject.fromObject(requestMap);
			log.info("xml请求解析====>>>" + jsonObject.toString());
			// 发送方账号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众账号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 默认回复此文本消息
			TextMessage defaultTextMessage = new TextMessage();
			defaultTextMessage.setToUserName(fromUserName);
			defaultTextMessage.setFromUserName(toUserName);
			defaultTextMessage.setCreateTime(new Date().getTime());
			defaultTextMessage.setMsgType(G_MessageUtil.MESSSAGE_TYPE_TEXT);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
			defaultTextMessage.setContent("欢迎访问!");
			// defaultTextMessage.setContent(getMainMenu());
			// 将文本消息对象转换成xml字符串
			respMessage = G_MessageUtil.textMessageToXml(defaultTextMessage);

			// 文本消息
			if (msgType.equals(G_MessageUtil.MESSSAGE_TYPE_TEXT)) {
				// 回复文本消息
				TextMessage textMessage = new TextMessage();
				// 这里需要注意，否则无法回复消息给用户了
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(G_MessageUtil.MESSSAGE_TYPE_TEXT);
				respContent = "你发的消息是：" + requestMap.get("Content");
				textMessage.setContent(respContent);
				respMessage = G_MessageUtil.textMessageToXml(textMessage);
			}
			// 事件推送
			else if (msgType.equals(G_MessageUtil.MESSSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(G_MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					User user = userService.findByOpenid(fromUserName);
					com.alibaba.fastjson.JSONObject userJson = GZHUtils.getUserInfo(fromUserName);
					if (user == null) {
						userService.saveJsonUser(userJson);
					} else {
						userService.updateJsonUser(userJson, user);
					}
					defaultTextMessage.setContent(userJson.getString("nickname") + ",欢迎您!");
					respMessage = G_MessageUtil.textMessageToXml(defaultTextMessage);
				}
				// 取消订阅
				else if (eventType.equals(G_MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					//
					log.info("取消订阅");

				} else if (eventType.equals(G_MessageUtil.EVENT_TYPE_CLICK)) {
					// 自定义菜单消息处理
					log.info("自定义菜单消息处理");
					String eventKey = requestMap.get("EventKey");
					if (eventKey.equals("key1")) {
						log.info("自定义菜单消息处理====>" + GZHUtils.getAccessToken());
					} else if (eventKey.equals("key2")) {
						log.info("自定义菜单消息处理2");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		respMessage = respMessage.replaceAll("content", "Content");
		log.info("respMessage====>>" + respMessage);
		// 响应消息
		response.getWriter().print(respMessage);
	}

}