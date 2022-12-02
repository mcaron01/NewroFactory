package com.oxyl.NewroFactory.dto.front;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionDto {

	private int id;
	
	@NotBlank(message = "Veuillez entrer un titre !")
	@Size(max = 100, message = "Veuillez entrer un titre valide (maximum 100 caractères) !")
	private String title;

	@NotBlank(message = "Veuillez entrer un énoncé !")
	private String statement;

	@Valid
	private ChapterDto chapter;

	@JsonCreator
	public QuestionDto(@JsonProperty("id") int id, @JsonProperty("title") String title, @JsonProperty("statement") String statement, @JsonProperty("chapter") ChapterDto chapterDto) {
		this.id = id;
		this.title = title;
		this.statement = statement;
		this.chapter = chapterDto;
	}

	public QuestionDto() {

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

	public ChapterDto getChapter() {
		return chapter;
	}

	public void setChapter(ChapterDto chapter) {
		this.chapter = chapter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ChapterDto [id=" + id + ", title=" + title + ", statement=" + statement + ", chapter=" + chapter + "]";
	}

}
