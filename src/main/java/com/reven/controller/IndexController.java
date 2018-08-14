package com.reven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping({ "/index", "/" })
	public String index(Model model) {
		model.addAttribute("hello", "张三");
		return "index"; //返回 /templates/index.html页面
	}
}