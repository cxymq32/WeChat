package com.bkk.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		HttpSession session = req.getSession();
		// 获得用户请求的URI
		String path = req.getRequestURI();
		// 从session取得已经登录验证的凭证 我这里的demo用的是password来作为登录凭证
		Object password = (String) session.getAttribute("user");
		// login.jsp页面无需过滤(根据自己项目的要求来)

		// 也可以path.contains("login.jsp") 反正怎么精确怎么来就不多说了
		if (path.indexOf("/login") > -1 || path.indexOf("/sign") > -1) {// 注意：登录页面千万不能过滤
			// 不然过滤器就。。。。。自行调试不要偷懒！这样记忆深刻
			arg2.doFilter(req, resp);
			return;
		} else {// 如果不是login.jsp进行过滤
			if (password == null || "".equals(password)) {
				// 跳转到登陆页面
				resp.sendRedirect("/login");
			} else {
				// 已经登陆,继续此次请求
				arg2.doFilter(req, resp);
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
