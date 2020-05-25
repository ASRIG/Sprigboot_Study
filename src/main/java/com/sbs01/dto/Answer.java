package com.sbs01.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Answer {
	@Id
	@GeneratedValue
	private Long id;

	// 답변과 작성자의 관계는 Many To One 관계이다.
	@ManyToOne
	// 만들어지는 forienkey의 이름을 지정할 수 있음.
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	private User writer;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
	private Question question;

	// 만들어지는 시간 정의
	private LocalDateTime createDate;

	@Lob
	private String contents;

	public Answer() {
	}

	public Answer(User writer, Question question, String contents) {
		this.writer = writer;
		this.contents = contents;
		this.question = question;
		this.createDate = LocalDateTime.now();
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
		Answer other = (Answer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", writer=" + writer + ", createDate=" + createDate + ", contents=" + contents
				+ "]";
	}

	// 이러한 함수를 html에서 호출하고 싶으면, get을 빼고 앞을 소문자로만들기.
	public String getFormattedCreateDate() {
		if (createDate == null)
			return "";
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}

}
