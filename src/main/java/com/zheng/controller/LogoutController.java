package com.zheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/logout")
public class LogoutController extends BaseController{

	@RequestMapping(method=RequestMethod.GET)
	public String logout() {
//		getSubject().logout();
		getSession().invalidate(); //默认会自动调用上面一行代码
		return "/login";
	}
	
}
