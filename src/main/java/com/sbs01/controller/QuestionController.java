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
import com.sbs01.utils.Result;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	// 깔끔하게 중복을 제거하기 위한 Result 유틸
	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session))
			return Result.fail("로그인이 필요합니다.");
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser))
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		return Result.ok();
	}

	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session))
			throw new IllegalStateException("로그인이 필요합니다.");
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser))
			throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		return true;
	}

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
		// 이런식으로 Result를 써서 구현할 수도 있고 밑에처럼 써도 된다.
		Question question = questionRepository.findById(id).get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session, Model model) {
		Question question = questionRepository.findById(id).get();
		try {
			hasPermission(session, question);
			question.update(title, contents);
			// 이미 값이 있다면 업데이트 없을 경우 새로 생성한다.
			questionRepository.save(question);
			return String.format("redirect:/questions/%d", id);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}

//		// 로그인 여부 파악
//		if (!HttpSessionUtils.isLoginUser(session))
//			return "redirect:/users/loginForm";
//		// 내 글인지 파악
//		User sessionUser = HttpSessionUtils.getUserFromSession(session);
//		Question question = questionRepository.findById(id).get();
//		if (!question.isSameWriter(sessionUser))
//			return String.format("redirect:/");

	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession session, Model model) {
		Question question = questionRepository.findById(id).get();
		try {
			hasPermission(session, question);
			// 삭제 repository
			questionRepository.deleteById(id);
			return String.format("redirect:/");
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}

	}
}
