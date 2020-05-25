package com.sbs01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs01.dto.Question;
import com.sbs01.dto.User;

public interface QuestionRepository extends JpaRepository<Question, Long>{
	
}
