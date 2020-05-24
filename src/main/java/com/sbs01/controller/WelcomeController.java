package com.sbs01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
	@RequestMapping("")
	public String home() {
		return "index";
	}
	
	@RequestMapping("/")
	public String hello() {
		return "index";
	}
	
	@RequestMapping(value = "/helloworld")
	public String welcome(String name, int age, Model model) {
		System.out.println("name : " + name);
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "welcome";
	}
	
	
}
