package com.sbs01.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbs01.dto.User;

@Controller
public class UserController {
	private List<User> users = new ArrayList<>();
	
	@RequestMapping("/create")
	public String create(User user) {
		System.out.println(user);
		users.add(user);
		
		// 이건 Templates로 이동이 되게 하는 것.
		// 그냥 href를 이용해 html에서 이동하는 것은 static
		return "redirect:/list";
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", users);
		return "list";
	}
}
