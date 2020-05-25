package com.sbs01.utils;

import javax.servlet.http.HttpSession;

import com.sbs01.dto.User;

// 중복제거를 위한 Utils객체. 
public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "SessionUser";
	
	public static boolean isLoginUser(HttpSession session) {
		User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);
		if(sessionUser == null) return false;
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if(!isLoginUser(session)) return null;
		return (User) session.getAttribute(USER_SESSION_KEY);
	}
}
