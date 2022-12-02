package com.oxyl.NewroFactory.dto.back;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="answer")
public class AnswerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private int id;
	
	@Column(name="label", nullable = false)
	private String label;
	
	@Column(name="text", nullable = false)
	private String text;

	@Column(name="valid_answer", nullable = false)
	private int validAnswer;
	
	@ManyToOne
	@JoinColumn(name="question_id")
	private QuestionEntity question;

	public AnswerEntity() {
	}

	public AnswerEntity(int id, String label, String text, int validAnswer, QuestionEntity question) {
		this.id = id;
		this.label = label;
		this.text = text;
		this.validAnswer = validAnswer;
		this.question = question;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getValidAnswer() {
		return validAnswer;
	}

	public void setValidAnswer(int validAnswer) {
		this.validAnswer = validAnswer;
	}

	public QuestionEntity getQuestion() {
		return question;
	}

	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, label, text, validAnswer, question);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnswerEntity other = (AnswerEntity) obj;
		return id == other.id && Objects.equals(label, other.label) && Objects.equals(text, other.text) && validAnswer == other.validAnswer && Objects.equals(question, other.question);
	}
}