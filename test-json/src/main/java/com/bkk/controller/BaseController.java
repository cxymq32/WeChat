package com.bkk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkk.domain.User;
import com.bkk.service.OrderService;
import com.bkk.service.ShopService;
import com.bkk.service.UserService;

@Controller
public class BaseController {
	@Resource
	public UserService userService;
	@Resource
	public ShopService shopService;
	@Resource
	public OrderService orderService;

	public User getCurrUser(HttpSession session) {
		return (User) session.getAttribute("user");
	}

	@RequestMapping("/test")
	public String test(Model model, HttpSession session) {
		return "test";
	}

}
