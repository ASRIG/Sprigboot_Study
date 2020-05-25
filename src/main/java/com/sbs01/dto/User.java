package com.sbs01.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

// 데이터베이스에서 사용가능한 객체임을 나타내는 Entity 어노테이션
@Entity
public class User {
	// Primary Key를 주고싶은 경우 @Id 어노테이션 사용.
	@Id
	// 이 아이디값을 자동으로 1씩 증가시켜줌.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// JSON으로 만들어내도록 설정
	@JsonProperty
	private Long id;
	
	// 제한사항 설정.
	@Column(nullable = false, length=30, unique = true)
	@JsonProperty
	private String userId;
	
	// 비밀번호는 보내기 싫어!
	@JsonIgnore
	private String password;
	@JsonProperty
	private String name;
	@JsonProperty
	private String email;
	
	public User() {}
	public User(String userId, String password, String name, String email) {
		setUserId(userId); setPassword(password); setName(name); setEmail(email);
	}
	
	// Method
	public boolean matchedPassword(String password) {
		if(password == null) return false;
		return this.password.equals(password);
	}
	
	// G/S
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public void setName(String name) {
		this.name = name;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
