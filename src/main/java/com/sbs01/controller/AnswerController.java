package com.sbs01.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbs01.dto.Answer;
import com.sbs01.dto.Question;
import com.sbs01.dto.User;
import com.sbs01.repository.AnswerRepository;
import com.sbs01.repository.QuestionRepository;
import com.sbs01.utils.HttpSessionUtils;

@Controller
// 답변이 퀘스트에 종속관계인 경우 이렇게 쓰는것이 바람직
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return "/users/loginForm";
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(loginUser, question, contents);
		answerRepository.save(answer);
		return String.format("redirect:/questions/%d", questionId);
	}
}
