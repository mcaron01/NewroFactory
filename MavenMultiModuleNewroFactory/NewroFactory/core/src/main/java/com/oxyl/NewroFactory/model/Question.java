package com.oxyl.NewroFactory.model;

import java.util.Objects;

import com.oxyl.NewroFactory.model.Intern.InternBuilder;

public class Question {
	private int id;
	private String title;
	private String statement;
	private Chapter chapter;

	private Question(QuestionBuilder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.statement = builder.statement;
		this.chapter = builder.chapter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", statement=" + statement + ", chapter=" + chapter.getName()
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(chapter, id, statement, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return Objects.equals(chapter, other.chapter) && id == other.id && Objects.equals(statement, other.statement)
				&& Objects.equals(title, other.title);
	}

	public static class QuestionBuilder {
		private int id;
		private String title;
		private String statement;
		private Chapter chapter;

		public QuestionBuilder(String title, String statement, Chapter chapter) {
			this.title = title;
			this.statement = statement;
			this.chapter = chapter;
		}

		public QuestionBuilder id(int id) {
			this.id = id;
			return this;
		}

		public Question build() {
			Question question = new Question(this);
			validateQuestionObject(question);
			return question;
		}

		private void validateQuestionObject(Question question) {
			// Do some basic validations to check
			// if user object does not break any assumption of system
		}

	}

}
