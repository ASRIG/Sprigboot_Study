package com.sbs01.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// 데이터베이스에서 사용가능한 객체임을 나타내는 Entity 어노테이션
@Entity
public class User {
	// Primary Key를 주고싶은 경우 @Id 어노테이션 사용.
	@Id
	// 이 아이디값을 자동으로 1씩 증가시켜줌.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 제한사항 설정.
	@Column(nullable = false, length=30)
	private String userId;
	
	private String password;
	private String name;
	private String email;
	
	public User() {}
	
	
	public User(String userId, String password, String name, String email) {
		setUserId(userId); setPassword(password); setName(name); setEmail(email);
	}


	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}


	public void update(User updateUser) {
		this.password = updateUser.password;
		this.name = updateUser.name;
		this.email = updateUser.email;
	}
	
}
