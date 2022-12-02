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
@Table(name="question")
public class QuestionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private int id;
	
	@Column(name="title", nullable = false)
	private String title;
	
	@Column(name="statement", nullable = false)
	private String statement;
	
	@ManyToOne
	@JoinColumn(name="chapter_id")
	private ChapterEntity chapter;

	public QuestionEntity() {
	}

	public QuestionEntity(int id, String title, String statement, ChapterEntity chapter) {
		this.id = id;
		this.title = title;
		this.statement = statement;
		this.chapter = chapter;
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

	public ChapterEntity getChapter() {
		return chapter;
	}

	public void setChapter(ChapterEntity chapter) {
		this.chapter = chapter;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, statement, chapter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionEntity other = (QuestionEntity) obj;
		return id == other.id && Objects.equals(title, other.title) && Objects.equals(statement, other.statement) && Objects.equals(chapter, other.chapter);
	}
}