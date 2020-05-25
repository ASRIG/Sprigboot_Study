package com.sbs01.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		if (!HttpSessionUtils.isLoginUser(session))
			return "redirect:/users/loginForm";
		model.addAttribute("user", HttpSessionUtils.getUserFromSession(session));
		return "qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session))
			return "redirect:/users/loginForm";

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);

		questionRepository.save(newQuestion);

		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		Question question = questionRepository.findById(id).get();
		model.addAttribute("question", question);
		return "/qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		// 로그인 여부 파악
		if (!HttpSessionUtils.isLoginUser(session))
			return "redirect:/users/loginForm";
		// 내 글인지 파악
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		if (!question.isSameWriter(sessionUser))
			return String.format("redirect:/");

		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
		// 로그인 여부 파악
		if (!HttpSessionUtils.isLoginUser(session))
			return "redirect:/users/loginForm";
		// 내 글인지 파악
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		if (!question.isSameWriter(sessionUser))
			return String.format("redirect:/");

		question.update(title, contents);
		// 이미 값이 있다면 업데이트 없을 경우 새로 생성한다.
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession session) {
		// 로그인 여부 파악
		if (!HttpSessionUtils.isLoginUser(session))
			return "redirect:/users/loginForm";
		// 내 글인지 파악
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		if (!question.isSameWriter(sessionUser))
			return String.format("redirect:/");

		// 삭제 repository
		questionRepository.deleteById(id);
		return String.format("redirect:/");
	}
}
