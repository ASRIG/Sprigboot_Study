package com.sbs01.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 이런식으로 정의하는 것은 객체지향적으로 바함직하지 않음.
	// private String writer;

	// Question과 User의 관계는 Many To One 관계이다.
	@ManyToOne
	// 만들어지는 forienkey의 이름을 지정할 수 있음.
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;

	// mappedBy는 해당하는 Answer에서 정의한 ManytoOne의 변수이름으로 정의
	@OneToMany(mappedBy = "question")
	@OrderBy("id ASC")
	private List<Answer> answers;

	// 만들어지는 시간 정의
	private LocalDateTime createDate;

	private String title;
	@Lob
	private String contents;

	public Question() {
	}

	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}

	// 이러한 함수를 html에서 호출하고 싶으면, get을 빼고 앞을 소문자로만들기.
	public String getFormattedCreateDate() {
		if (createDate == null)
			return "";
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}

	public void update(String title2, String contents2) {
		this.title = title2;
		this.contents = contents2;
	}

	public boolean isSameWriter(User loginUser) {
		return this.writer.equals(loginUser);
	}
}
