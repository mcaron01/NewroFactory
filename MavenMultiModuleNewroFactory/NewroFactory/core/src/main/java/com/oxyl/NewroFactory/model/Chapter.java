package com.oxyl.NewroFactory.model;

import java.util.Objects;

import com.oxyl.NewroFactory.model.Question.QuestionBuilder;

public class Chapter {
	private int id;
	private String name;
	private String parentPath;

	private Chapter(ChapterBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.parentPath = builder.parentPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	@Override
	public String toString() {
		return "Chapter [id=" + id + ", name=" + name + ", parentPath=" + parentPath + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, parentPath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chapter other = (Chapter) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(parentPath, other.parentPath);
	}

	public static class ChapterBuilder {
		private int id;
		private String name;
		private String parentPath;

		public ChapterBuilder(String name, String parentPath) {
			this.name = name;
			this.parentPath = parentPath;
		}

		public ChapterBuilder id(int id) {
			this.id = id;
			return this;
		}

		public Chapter build() {
			Chapter chapter = new Chapter(this);
			validateChapterObject(chapter);
			return chapter;
		}

		private void validateChapterObject(Chapter chapter) {
			// Do some basic validations to check
			// if user object does not break any assumption of system
		}

	}

}
