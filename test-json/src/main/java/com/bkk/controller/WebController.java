package com.bkk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bkk.domain.Shop;
import com.bkk.domain.User;
import com.bkk.service.OrderService;
import com.bkk.service.ShopService;
import com.bkk.service.UserService;

@Controller
@RequestMapping("/admin")
public class WebController extends BaseController {

	/** 登陆 */
	@RequestMapping("/login")
	public String login(Model model, User user, HttpSession session) {
		User cuser = userService.findByNameAndPass(user);
		if (cuser != null) {
			session.setAttribute("user", cuser);
			return "redirect:/admin/index";
		}
		if (user != null && user.getUsername() != null)
			model.addAttribute("errMsg", "用户名密码不正确！");
		return "login";
	}

	/** 首页 */
	@RequestMapping("/index")
	public String index(Model model, HttpSession session) {
		List<Shop> shopList = shopService.getByUserId(this.getCurrUser(session).getId());
		if (shopList.size() > 0)
			model.addAttribute("shop", shopList.get(0));
		return "index";
	}

	/** 注册页面 */
	@RequestMapping("/register")
	public String register(HttpSession session) {
		return "register";
	}

	/** 注册 */
	@RequestMapping("/registerS")
	public String registerS(User user, HttpSession session) {
		userService.save(user);
		return "redirect:/admin/login";
	}

	/** 检查用户名是否存在 1存在0不存在 */
	@ResponseBody
	@RequestMapping("/checkUname")
	public int checkUname(String uname, HttpSession session) {
		List<User> list = userService.checkUname(uname);
		return list.size() > 0 ? 1 : 0;
	}

	/** 退出登陆 */
	@RequestMapping("/loginOut")
	public String loginOut(Model model, User user, HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/admin/login";
	}

	/** 保存店铺 */
	@RequestMapping("/modifyShop")
	public String save(Model model, HttpSession session, Shop shop) {
		if (shop.getLocation() == "")
			shop.setLocation("37.367727,118.020893");
		String[] location = shop.getLocation().split(",");
		shop.setLatitude(Double.parseDouble(location[0]));
		shop.setLongitude(Double.parseDouble(location[1]));
		if (shop.getId() == null) {
			shop.setUserId(getCurrUser(session).getId());
			shopService.save(shop);
		} else {
			shopService.update(shop);
		}
		model.addAttribute("shop", shop);
		return "redirect:/admin/index";
	}
}
