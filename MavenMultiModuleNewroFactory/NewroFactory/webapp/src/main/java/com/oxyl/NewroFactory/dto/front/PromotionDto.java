package com.oxyl.NewroFactory.dto.front;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oxyl.NewroFactory.validation.isInListPromotion;

public class PromotionDto {

	@NotBlank(message = "Veuillez entrer un nom de formation !")
	@Size(min = 1, max = 30, message = "Veuillez entrer un nom de formation valide (maximum 30 caractères) !")
	private String name;

	@isInListPromotion
	private int id;

	@JsonCreator
	public PromotionDto(@JsonProperty("id") int id,@JsonProperty("name")  String name) {
		this.id = id;
		this.name = name;
	}

	public PromotionDto() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
