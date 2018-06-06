package com.bkk.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bkk.common.G_MessageUtil;
import com.bkk.common.UtilsGZH;
import com.bkk.common.UtilsXCX;
import com.bkk.common.base.DloadImgUtil;
import com.bkk.common.base.MyProperties;
import com.bkk.common.base.MyString;
import com.bkk.common.base.MyXML;
import com.bkk.common.msg.TextMessage;
import com.bkk.domain.Order;
import com.bkk.domain.Shop;
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

	@ResponseBody
	@RequestMapping("/downloadImage")
	public void downloadImage(Model model, String mediaId, int shopId, HttpServletRequest request) {
		String savePath = this.getClass().getClassLoader().getResource("").getPath();
		savePath = savePath.substring(0, savePath.indexOf("/WEB-INF")) + "/resources/pic";
		log.info("mediaId==================>>>>" + mediaId);
		log.info("savePath==================>>>>" + savePath);
		log.info("shopId==================>>>>" + shopId);
		String fileName = DloadImgUtil.downloadMedia(UtilsGZH.getAccessToken(), mediaId, savePath);
		String url = request.getRequestURL().toString();
		url = url.substring(0, url.indexOf("/centercontroller/")) + "/resources/pic/" + fileName;
		log.info("savePath==========url========>>>>" + url);
	}

	/** 我的店铺 */
	@RequestMapping("/myShop")
	public String myShop(Model model, String code, String state, HttpServletRequest request, HttpSession session)
			throws Exception {
		long start = System.currentTimeMillis();
		log.info("===>>WX回调带回code=" + code + "\t state=" + state);
		User u = getUserFromSeesionOrCode(code, session);// 获取用户信息
		log.info("===>>user=" + u);
		if (u != null && u.getShopId() != null) {
			Shop shop = shopService.findById(Shop.class, u.getShopId());
			log.info("ShopName===========>" + shop.getShopName());
			model.addAttribute("shop", shop);
		} else {
			return "gzh/firstLogin";
		}
		String url = request.getRequestURL().toString() + "?" + request.getQueryString();
		model = UtilsGZH.getJSapi(model, url);// 获取网页授权jsapi
		log.warn("myshop--use--time=========>>" + (System.currentTimeMillis() - start) / 1000);
		return "gzh/myShop";
	}

	/** 绑定店铺 */
	@ResponseBody
	@RequestMapping("/binding")
	public int binding(Model model, String shopCode, HttpSession session) throws Exception {
		log.info("binding=====code======>" + shopCode);
		List<Shop> shoplist = shopService.getByShopCode(shopCode);
		if (shoplist.size() > 0) {
			User u = (User) session.getAttribute("cuser");
			log.info("session=binding==user==>>" + u);
			if (u != null) {
				u.setShopId(shoplist.get(0).getId());
				log.info("update===user==>>" + u.getShopId());
				userService.update(u);
				return 1;
			} else {
				return -1;
			}
		}
		return 0;
	}

	/** 预约列表 */
	@RequestMapping("/orderList")
	public String orderList(Model model, String code, String state, HttpSession session) throws Exception {
		log.info("===>>WX回调带回code=" + code + "\t state=" + state);
		User u = getUserFromSeesionOrCode(code, session);
		if (u != null) {
			List<Order> listOrder = orderService.getByShopId(u.getShopId(), state);
			model.addAttribute("listOrder", listOrder);
		}
		model.addAttribute("state", state);
		return "gzh/orderList";
	}

	/** 从seesion中或者重新获取用户 */
	public User getUserFromSeesionOrCode(String code, HttpSession session) throws Exception {
		String openid = (String) session.getAttribute("openid");
		log.info("get==openid===from===session=====>>" + openid);
		if (MyString.isNotEmpty(openid)) {// 不是微信底部跳转，页面刷新时
			User u = (User) session.getAttribute("cuser");
			log.info("get==user==from==session=====>>" + u);
			if (u != null && u.getShopId() != null) {
				return u;
			} else {
				u = userService.findByOpenid(openid);
				session.setAttribute("cuser", u);
				return u;
			}
		} else if (MyString.isNotEmpty(code)) {// 微信底部跳转回调链接
			String json = UtilsGZH.getUserOpenId(code);
			com.alibaba.fastjson.JSONObject jsonObejct = JSON.parseObject(json);
			openid = (String) jsonObejct.get("openid");
			if (MyString.isNotEmpty(openid)) {
				session.setAttribute("openid", openid);
				User u = userService.findByOpenid(openid);
				log.info("userService===get===user=======>>" + u);
				session.setAttribute("cuser", u);
				if (u != null && u.getShopId() != null) {
					log.info("get===user=======>>" + u.getId());
					return u;
				}
			}
		}
		log.info("===notfindUser=======");
		return null;

	}

	/** 操作预约列表 */
	@RequestMapping("/operateOrder")
	public String operateOrder(Model model, long id, Integer state, HttpSession session) throws Exception {
		Order order = orderService.findById(Order.class, id);
		order.setStatus(state);
		orderService.update(order);
		// 发送消息给小程序用户
		JSONObject packageParams = new JSONObject();
		packageParams.put("touser", order.getOpenId());
		// FIXME 预订成功后给小程序用户发送的模版消息
		packageParams.put("template_id", "Djsyb6FjccRPJ2uxWXcpdr8s5-K7YqSLiGvwwtSEXHY");
		packageParams.put("form_id", order.getFormId());
		packageParams.put("page", "pages/myorder/myorder");
		JSONObject data = new JSONObject();
		data.put("keyword1", "{ 'value': '" + order.getShopName() + "', 'color': '#4a4a4a' }");
		data.put("keyword2", "{ 'value': '" + order.getOrderNum() + "', 'color': '#4a4a4a' }");
		data.put("keyword3", "{ 'value': '" + order.getPhone() + "', 'color': '#4a4a4a' }");
		data.put("keyword4", "{ 'value': '" + order.getRemark() + "', 'color': '#4a4a4a' }");
		packageParams.put("data", data.toString());
		log.info("sendToXCXUser============>>" + packageParams.toString());
		UtilsXCX.sengMsg(packageParams.toString());
		return "redirect:/centercontroller/orderList?state=1";
	}

	/** 创建菜单createMenu?code=wxb90a701330e3bab8 */
	@RequestMapping("/createMenu")
	public void createMenu(Model model, String code, HttpSession session) throws Exception {
		if (code.equals(MyProperties.getProperties("my.properties", "g_appid"))) {
			log.info("#############Menu Initialize###############");
			UtilsGZH.createMenu();
			log.info("#############Menu Initialize END###############");
		}
	}

	@RequestMapping(value = "/startService", method = RequestMethod.GET)
	public void doGet(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr, HttpServletResponse response)
			throws Exception {
		log.info("收到微信get验证: signature" + signature + "\ttimestamp:" + timestamp + "\tnonce:" + nonce + "\techostr:"
				+ echostr);
		if (UtilsGZH.checkSignature(signature, timestamp, nonce)) {
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
			Map<String, String> requestMap = MyXML.pareXml(request.getInputStream());
			JSONObject jsonObject = JSONObject.fromObject(requestMap);
			log.info("xml请求解析====>>>" + jsonObject.toString());
			UtilsGZH.reciveMsg(jsonObject.toString());// 追加到msg/msg.txt
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
				String content = requestMap.get("Content");
				respContent = "你发的消息是：" + requestMap.get("Content");
				textMessage.setContent(respContent);
				respMessage = G_MessageUtil.textMessageToXml(textMessage);
				respMessage = respMessage.replaceAll("content", "Content");
			}
			// link
			else if (msgType.equals(G_MessageUtil.MESSSAGE_TYPE_LINK)) {
				respMessage = "<xml><ToUserName>< ![CDATA[toUser] ]></ToUserName><FromUserName>< ![CDATA[fromUser] ]></FromUserName><CreateTime>12345678</CreateTime><MsgType>< ![CDATA[news] ]></MsgType><ArticleCount>#</ArticleCount><Articles><item><Title>< ![CDATA[title] ]></Title><Description>< ![CDATA[description] ]></Description><PicUrl>< ![CDATA[picurl] ]></PicUrl><Url>< ![CDATA[url] ]></Url></item></Articles></xml>";
				respMessage = respMessage.replace("[toUser]", "[" + fromUserName + "]");
				respMessage = respMessage.replace("[fromUser]", "[" + toUserName + "]");
				respMessage = respMessage.replace("12345678", new Date().getTime() + "");
				respMessage = respMessage.replace("#", 1 + "");
				respMessage = respMessage.replace("title", "test");
				respMessage = respMessage.replace("description", "test1");
				respMessage = respMessage.replace("picurl",
						"https://csdnimg.cn/release/edu/resource/images/trial_member_product.gif");
				respMessage = respMessage.replace("url", "www.baidu.com");
			}
			// 事件推送
			else if (msgType.equals(G_MessageUtil.MESSSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(G_MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					User user = userService.findByOpenid(fromUserName);
					com.alibaba.fastjson.JSONObject userJson = UtilsGZH.getUserInfo(fromUserName);
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
						log.info("自定义菜单消息处理====>" + UtilsGZH.getAccessToken());
					} else if (eventKey.equals("key2")) {
						log.info("自定义菜单消息处理2");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("respMessage====>>" + respMessage);
		// 响应消息
		response.getWriter().print(respMessage);
	}

}