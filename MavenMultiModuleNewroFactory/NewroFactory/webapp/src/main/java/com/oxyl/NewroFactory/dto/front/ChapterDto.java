package com.oxyl.NewroFactory.dto.front;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oxyl.NewroFactory.validation.isInListChapter;

public class ChapterDto {

	@isInListChapter
	private int id;

	@NotBlank(message = "Veuillez entrer un nom de chapitre !")
	@Size(min = 1, max = 100, message = "Veuillez entrer un nom de chapitre valide (maximum 100 caractères) !")
	private String name;
	
	@NotBlank(message = "Veuillez entrer un nom de chapitre !")
	@Size(min = 1, max = 100, message = "Veuillez entrer un nom de chapitre valide (maximum 100 caractères) !")
	private String parentPath;

	private List<ChapterDto> children;
	
	@JsonCreator
	public ChapterDto(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("parentPath") String parentPath,
			@JsonProperty("children") List<ChapterDto> children) {
		this.id = id;
		this.name = name;
		this.parentPath = parentPath;
		this.children = children;
		}

	public ChapterDto() {
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

	public List<ChapterDto> getChildren() {
		return children;
	}

	public void setChildren(List<ChapterDto> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "ChapterDto [id=" + id + ", name=" + name + ", parentPath=" + parentPath + ", children=" + children
				+ "]";
	}
	
	
	
}
