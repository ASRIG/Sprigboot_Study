package com.sbs01.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbs01.dto.User;
import com.sbs01.repository.UserRepository;
import com.sbs01.utils.HttpSessionUtils;

@Controller
// 중복을 제거할 수 있다. 대표 URL
@RequestMapping("/users")
public class UserController {
	private List<User> users = new ArrayList<>();

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if(user == null)
			return "redirect:/users/loginForm";
		
		// 중복제거를 위한 로직처리를 할 수 있음
		if(!user.matchedPassword(password))
			return "redirect:/users/loginForm";
		
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 해당하는 애트리뷰트를 제거하거나
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		// invalidate 해버리기
//		session.invalidate();
		return "redirect:/";
	}

	@PostMapping("")
	public String create(User user) {
		System.out.println(user);

		// users.add(user);
		// 넣는 것. (Insert)
		userRepository.save(user);

		// 이건 Templates로 이동이 되게 하는 것.
		// 그냥 href를 이용해 html에서 이동하는 것은 static
		return "redirect:/users";
	}

	@GetMapping("")
	public String list(Model model) {
		// select All
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/form")
	public String goform() {
		return "/user/form";
	}
	
	@RequestMapping("/index")
	public String goIndex() {
		return "/index";
	}
	
	@RequestMapping("/profile")
	public String goProfile() {
		return "/user/profile";
	}
	
	@RequestMapping("/loginForm")
	public String goLogin() {
		return "/user/login";
	}
	
	
	// id에 대한 정보에 대한 form에 해당하는 컨트롤러를 만들 수 있다.
	@GetMapping("/{id}/form")
	// path의 값을 변수로 사용할 때, PathVariable 어노테이션 사용 위의 {}안에 있는 값을 그대로 밑에 적어야함.
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return "redirect:/users/loginForm";
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		if(sessionUser.getId() != id) return "redirect:/users/loginForm";
		
		// 한개만 찾아서 전달
		System.out.println("[{id}/form Start]");
		User user = userRepository.findById(id).get();
		System.out.println(user);
		model.addAttribute("user", user);
		return "/user/updateform";
	}
	
	// Post로 해도 괜찮지만 hidden을 이용해 원래 HTTP프로토콜에 맞는 putmapping 해주기
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updateUser, HttpSession session) {
		System.out.println("[{id} Start]");
		
		if(!HttpSessionUtils.isLoginUser(session)) return "redirect:/users/loginForm";
		User user = HttpSessionUtils.getUserFromSession(session);
		
		System.out.println("\t[update before]");
		System.out.println(user);
		user.update(updateUser);
		System.out.println("\t[update after]");
		System.out.println(user);
		
		// 현재 해당하는 아이디 값이 없으면 추가를 하고 있으면 업데이트를 한다.
		userRepository.save(user);
		return "redirect:/users";
		// 27장
	}
	
}
