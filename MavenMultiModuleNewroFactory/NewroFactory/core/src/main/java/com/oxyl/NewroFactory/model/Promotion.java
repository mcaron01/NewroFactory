package com.oxyl.NewroFactory.model;

import java.util.Objects;

public class Promotion {
	private int id;
	private String name;

	private Promotion(PromotionBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Promotion other = (Promotion) obj;
		return id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Promotion [id=" + id + ", name=" + name + "]";
	}

	public static class PromotionBuilder {
		private int id;
		private String name;

		public PromotionBuilder(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		public Promotion build() {
			Promotion promotion = new Promotion(this);
			validatePromotionObject(promotion);
			return promotion;
		}

		private void validatePromotionObject(Promotion promotion) {
			// Do some basic validations to check
			// if user object does not break any assumption of system
		}

	}

}
