package com.oxyl.NewroFactory.model;

import java.util.Objects;

import com.oxyl.NewroFactory.model.Question.QuestionBuilder;

public class Answer {
	private int id;
	private String label;
	private String text;
	private int validAnswer;
	private Question question;
	
	private Answer(AnswerBuilder builder) {
		this.id = builder.id;
		this.label = builder.label;
		this.text = builder.text;
		this.validAnswer = builder.validAnswer;
		this.question = builder.question;
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
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
	@Override
	public String toString() {
		return "Answer [id=" + id + ", label=" + label + ", text=" + text + ", validAnswer=" + validAnswer
				+ ", questionId=" + question.toString() + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, label, question, text, validAnswer);
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
		return id == other.id && Objects.equals(label, other.label) && question.equals(other.question)
				&& Objects.equals(text, other.text) && validAnswer == other.validAnswer;
	}
	
	public static class AnswerBuilder {
		private int id;
		private String label;
		private String text;
		private int validAnswer;
		private Question question;

		public AnswerBuilder(String label, String text, int validAnswer, Question question) {
			this.label = label;
			this.text = text;
			this.validAnswer = validAnswer;
			this.question = question;
		}

		public AnswerBuilder id(int id) {
			this.id = id;
			return this;
		}

		public Answer build() {
			Answer answer = new Answer(this);
			validateAnswerObject(answer);
			return answer;
		}

		private void validateAnswerObject(Answer answer) {
			// Do some basic validations to check
			// if user object does not break any assumption of system
		}
	}

}
