package com.zheng.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.zheng.domain.User;
import com.zheng.service.UserService;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

	private UserService userService = null;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		boolean result = super.onAccessDenied(request, response);
		if(!result) {
			//登录成功需要把用户信息保存到session中
			String username = (String) getSubject(request, response).getPrincipal();
			User user = userService.findByUsername(username);
			HttpServletRequest req = (HttpServletRequest) request;
			req.getSession().setAttribute("user", user);
		}
		
		return result;
	}

}
