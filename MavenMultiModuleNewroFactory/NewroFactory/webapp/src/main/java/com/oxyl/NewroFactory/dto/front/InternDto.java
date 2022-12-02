package com.oxyl.NewroFactory.dto.front;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oxyl.NewroFactory.validation.isDateFinFormationBeforeDateArrivee;
import com.oxyl.NewroFactory.validation.isInListPromotion;

@isDateFinFormationBeforeDateArrivee
public class InternDto {

	private int id;
	
	@NotBlank(message = "Veuillez entrer un prénom !")
	@Size(max = 30, message = "Veuillez entrer un prénom valide (maximum 30 caractère) !")
	private String firstName;

	@NotBlank(message = "Veuillez entrer un nom !")
	@Size(max = 30, message = "Veuillez entrer un nom valide (maximum 30 caractère) !")
	private String lastName;

	@NotNull(message = "Veuillez entrer une date d'arrivée !")
	@Past(message= "La date d'arrivée est dans le futur !")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateArrivee;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateFinFormation;
	
	@Valid
	private PromotionDto promotion;

	@JsonCreator
	public InternDto(@JsonProperty("id") int id,@JsonProperty("firstName")  String firstName, @JsonProperty("lastName")  String lastName,
			@JsonProperty("dateArrivee") LocalDate dateArrive,@JsonProperty("dateFinFormation") LocalDate dateFinFormation,
			@JsonProperty("promotion") PromotionDto promotionDto) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateArrivee = dateArrive;
		this.dateFinFormation = dateFinFormation;
		this.promotion = promotionDto;
	}

	public InternDto() {

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateArrivee() {
		return dateArrivee;
	}

	public void setDateArrivee(LocalDate dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public LocalDate getDateFinFormation() {
		return dateFinFormation;
	}

	public void setDateFinFormation(LocalDate dateFinFormation) {
		this.dateFinFormation = dateFinFormation;
	}

	public PromotionDto getPromotion() {
		return promotion;
	}

	public void setPromotion(PromotionDto promotion) {
		this.promotion = promotion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "InternDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateArrivee="
				+ dateArrivee + ", dateFinFormation=" + dateFinFormation+ ", promotion=" + promotion + "]";
	}
	
	
	
	
}
