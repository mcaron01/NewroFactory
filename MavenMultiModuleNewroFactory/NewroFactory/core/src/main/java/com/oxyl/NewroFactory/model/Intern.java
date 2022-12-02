package com.oxyl.NewroFactory.model;

import java.time.LocalDate;
import java.util.Objects;

public class Intern {
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate dateArrivee;
	private LocalDate dateFinFormation;
	private Promotion promotion;

	private Intern(InternBuilder builder) {
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.id = builder.id;
		this.dateArrivee = builder.dateArrivee;
		this.dateFinFormation = builder.dateFinFormation;
		this.promotion = builder.promotion;
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

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
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
		Intern other = (Intern) obj;
		return Objects.equals(dateArrivee, other.dateArrivee) && Objects.equals(dateFinFormation, other.dateFinFormation)
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(promotion, other.promotion);
	}

	@Override
	public String toString() {
		return "Intern [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", arrival=" + dateArrivee
				+ ", formation=" + dateFinFormation + ", promotion=" + promotion.getName() + "]";
	}

	public static class InternBuilder {
		private int id;
		private String firstName;
		private String lastName;
		private LocalDate dateArrivee;
		private LocalDate dateFinFormation;
		private Promotion promotion;

		public InternBuilder(String firstName, String lastName, LocalDate dateArrivee, Promotion promotion) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.dateArrivee = dateArrivee;
			this.promotion = promotion;
		}

		public InternBuilder id(int id) {
			this.id = id;
			return this;
		}

		public InternBuilder dateFinFormation(LocalDate dateFinFormation) {
			this.dateFinFormation = dateFinFormation;
			return this;
		}

		public Intern build() {
			Intern intern = new Intern(this);
			validateInternObject(intern);
			return intern;
		}

		private void validateInternObject(Intern intern) {
			// Do some basic validations to check
			// if user object does not break any assumption of system
		}

	}

}
