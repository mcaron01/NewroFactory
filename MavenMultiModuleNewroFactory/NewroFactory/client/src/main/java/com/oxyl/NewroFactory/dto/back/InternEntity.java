package com.oxyl.NewroFactory.dto.back;

import java.time.LocalDate;
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
@Table(name="intern")
public class InternEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private int id;
	
	@Column(name="first_name", nullable = true)
	private String firstName;
	
	@Column(name="last_name", nullable = true)
	private String lastName;
	
	@Column(name="arrival", nullable = true)
	private LocalDate dateArrivee;
	
	@Column(name="formation_over", nullable = true)
	private LocalDate dateFinFormation;
	
	@ManyToOne
	@JoinColumn(name="promotion_id")
	private PromotionEntity promotion;

	public InternEntity() {
		super();
	}

	public InternEntity(int id, String firstName, String lastName, LocalDate dateArrivee, LocalDate dateFinFormation,
			PromotionEntity promotion) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateArrivee = dateArrivee;
		this.dateFinFormation = dateFinFormation;
		this.promotion = promotion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public PromotionEntity getPromotion() {
		return promotion;
	}

	public void setPromotion(PromotionEntity promotion) {
		this.promotion = promotion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateArrivee, dateFinFormation, firstName, id, lastName, promotion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InternEntity other = (InternEntity) obj;
		return Objects.equals(dateArrivee, other.dateArrivee) && Objects.equals(dateFinFormation, other.dateFinFormation)
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(promotion, other.promotion);
	}
}