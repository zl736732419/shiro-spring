package com.zheng.controller;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	@RequestMapping(method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String doLogin() {
		String shiroLoginFailure = (String) getRequest().getAttribute("shiroLoginFailure");
		if(shiroLoginFailure != null) {
			String error = null;
			if(shiroLoginFailure.equals(UnknownAccountException.class.getName())) {
				error = "用户名不存在!";
			}else if(shiroLoginFailure.equals(LockedAccountException.class.getName())) {
				error = "当前用户已被禁用!";
			}else if(shiroLoginFailure.equals(IncorrectCredentialsException.class.getName())) {
				error = "用户名/密码错误!";
			}else if(shiroLoginFailure.equals(ExcessiveAttemptsException.class.getName())) {
				error = "登录次数过于频繁，请稍后再试!";
			}else if(shiroLoginFailure.equals(ExpiredCredentialsException.class.getName())) {
				error = "密码已失效!";
			}else {
				error = "遇到错误!";
			}
			
			System.out.println(getUser());
			
			putRequestContext("error", error);
			return "login";
		}

		return "main";
		
	}
}
