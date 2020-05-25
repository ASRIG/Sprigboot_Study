package com.sbs01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbs01.repository.QuestionRepository;

@Controller
public class WelcomeController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@RequestMapping("")
	public String home(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
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
