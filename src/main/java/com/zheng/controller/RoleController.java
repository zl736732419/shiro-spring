package com.zheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/role")
public class RoleController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String role() {
		return "role";
	}

}
