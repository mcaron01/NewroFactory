package com.oxyl.NewroFactory.dto.back;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="chapter")
public class ChapterEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private int id;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column(name="parent_path", nullable = false)
	private String parentPath = "/";
	
	public ChapterEntity() {
	}

	public ChapterEntity(int id, String name, String parentPath) {
		this.id = id;
		this.name = name;
		this.parentPath = parentPath;
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
		ChapterEntity other = (ChapterEntity) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(parentPath, other.parentPath);
	}

	@Override
	public String toString() {
		return "ChapterEntity [id=" + id + ", name=" + name + ", parentPath=" + parentPath + "]";
	}
	
	
}