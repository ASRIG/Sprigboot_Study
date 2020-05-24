package com.sbs01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs01.dto.User;

// JpaRepository<T, S> -> T는 어떤 클래스의 repository인지 명시. S : 유저의 primary key의 타입이 무엇이냐
public interface UserRepository extends JpaRepository<User, Long>{
}
