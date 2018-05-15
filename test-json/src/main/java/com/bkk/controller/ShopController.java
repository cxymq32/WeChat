package com.bkk.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bkk.common.MyDate;
import com.bkk.common.Page;
import com.bkk.domain.Order;
import com.bkk.domain.Shop;

@Controller
@RequestMapping("/wx")
public class ShopController extends BaseController {
	Logger logger = Logger.getLogger(ShopController.class);

	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");

	@ResponseBody
	@RequestMapping("/comfirorder")
	public boolean comfirorder(Model model, Order order, HttpSession session) {
		order.setCreateTime(new Date());
		String time = order.getArriveTime();
		if (time.indexOf("今天") > -1)
			order.setArriveTime(sf.format(new Date()) + time.substring(2)+"分");
		if (time.indexOf("明天") > -1)
			order.setArriveTime(MyDate.getNextDay(new Date()) + time.substring(2)+"分");
		orderService.save(order);
		System.out.println("给商户发消息。。。。");
		return true;
	}

	@ResponseBody
	@RequestMapping("/getShopByPage")
	public Object getShopByPage(Page page, Shop shop, HttpSession session) {
		List<Shop> list = shopService.getByPage(page, shop);
		logger.info("list===>" + list.size());
		return list;
	}

	@ResponseBody
	@RequestMapping("/getOrderByPage")
	public Object getOrderByPage(Page page, Order order, HttpSession session) {
		List<Order> list = orderService.getByPage(page, order);
		return list;
	}

}
