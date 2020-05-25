package com.sbs01.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbs01.dto.Question;
import com.sbs01.dto.User;
import com.sbs01.repository.QuestionRepository;
import com.sbs01.utils.HttpSessionUtils;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	
	@GetMapping("/form")
	public String form(HttpSession session, Model model) {
		if(!HttpSessionUtils.isLoginUser(session)) return "redirect:/users/loginForm";
		model.addAttribute("user", HttpSessionUtils.getUserFromSession(session));
		return "qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return "redirect:/users/loginForm";
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
		
		questionRepository.save(newQuestion);
		
		return "redirect:/";
	}
}
