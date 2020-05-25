package com.sbs01.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbs01.dto.Answer;
import com.sbs01.dto.Question;
import com.sbs01.dto.User;
import com.sbs01.repository.AnswerRepository;
import com.sbs01.repository.QuestionRepository;
import com.sbs01.utils.HttpSessionUtils;
import com.sbs01.utils.Result;

@RestController
// 답변이 퀘스트에 종속관계인 경우 이렇게 쓰는것이 바람직
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return null;
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		question.addAnswer();
		Answer answer = new Answer(loginUser, question, contents);
		// save를 들어가보면 returnType이 자기자신객체(answer 저장된)
		return answerRepository.save(answer);
	}
	
	@DeleteMapping("{id}")
	public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return Result.fail("로그인해야 합니다");
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Answer answer = answerRepository.findById(id).get();
		if(!answer.isSameWriter(loginUser)) return Result.fail("자기꺼만 삭제합니다");
		Question question = questionRepository.findById(questionId).get();
		question.deleteAnswer();
		// 끝나고 해당하는 내용을 세이브 하는 과정이 필요.
		questionRepository.save(question);
		answerRepository.deleteById(id);
		return Result.ok();
	}
	
}
