package com.zheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zheng.bind.CurrentUser;
import com.zheng.domain.User;

@Controller
@RequestMapping(value="/main")
public class MainController extends BaseController {

	@RequestMapping(method=RequestMethod.GET)
	public String main(@CurrentUser User user) {
		System.out.println(user);
		return "main";
	}
	
}
