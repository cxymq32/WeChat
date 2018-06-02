package com.bkk.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bkk.common.UtilsGZH;
import com.bkk.common.base.MyDate;
import com.bkk.common.base.MyPage;
import com.bkk.common.base.MyRedis;
import com.bkk.domain.Order;
import com.bkk.domain.Shop;
import com.bkk.domain.User;

@Controller
@RequestMapping("/wx")
public class ShopController extends BaseController {
	Logger log = Logger.getLogger(ShopController.class);

	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
	public static SimpleDateFormat sfd = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");

	/** 预约下单 */
	@ResponseBody
	@RequestMapping("/comfirorder")
	public boolean comfirorder(Model model, Order order, HttpSession session) throws Exception {
		Shop shop = shopService.findById(Shop.class, order.getShopId());
		order.setShopName(shop.getShopName());
		order.setShopImage(shop.getMainImage());
		order.setCreateTime(new Date());
		order.setOrderNum(System.currentTimeMillis());

		// 设置到店时间
		String time = order.getArriveTime();
		String date = "";
		if (time.indexOf("今天") > -1)
			date = sf.format(new Date()) + time.substring(2) + "分";
		if (time.indexOf("明天") > -1)
			date = MyDate.getNextDay(new Date()) + time.substring(2) + "分";
		order.setArriveTimeDate(sfd.parse(date));
		orderService.save(order);
		log.info("给商户发消息。。。。");
		List<User> listu = userService.findByShopId(order.getShopId());
		JSONObject jsonContent = null;
		JSONObject content = null;
		for (User u : listu) {
			jsonContent = new JSONObject();
			jsonContent.put("touser", u.getOpenid());
			jsonContent.put("msgtype", "text");
			content = new JSONObject();
			String str = order.getArriveTime() + "将有" + order.getPeople() + "人到店，联系电话：" + order.getPhone() + "，备注："
					+ order.getRemark();
			content.put("content", str);
			jsonContent.put("text", content);
			log.info("jsonContent.toJSONString()=====>" + jsonContent.toJSONString());
			UtilsGZH.sendMsg(jsonContent.toJSONString());
		}
		return true;
	}

	/** 取消 */
	@ResponseBody
	@RequestMapping("/cancelOrder")
	public Object cancelOrder(long orderId, HttpSession session) {
		Order order = orderService.findById(Order.class, orderId);
		order.setStatus(-1);
		orderService.update(order);
		return true;
	}

	/** 记录登陆小程序的人 */
	@ResponseBody
	@RequestMapping("/markNewUser")
	public Object markNewUser(String userInfo, String openid, HttpSession session) {
		log.info("markNewUser###" + openid + "==" + userInfo);
		log.info("markNewUser###" + MyRedis.get(openid) == null);
		if (openid != null && MyRedis.get(openid) == null) {
			MyRedis.set(openid, userInfo, 0);
		}
		return true;

	}

	/** index获取shop列表 */
	@ResponseBody
	@RequestMapping("/getShopByPage")
	public Object getShopByPage(MyPage page, Shop shop, HttpSession session) {
		List<Shop> list = shopService.getByPage(page, shop);
		log.info("list===>" + list.size());
		return list;
	}

	/** MyOrder获取order列表 */
	@ResponseBody
	@RequestMapping("/getOrderByPage")
	public Object getOrderByPage(MyPage page, Order order, HttpSession session) {
		List<Order> list = orderService.getByPage(page, order);
		return list;
	}

}
