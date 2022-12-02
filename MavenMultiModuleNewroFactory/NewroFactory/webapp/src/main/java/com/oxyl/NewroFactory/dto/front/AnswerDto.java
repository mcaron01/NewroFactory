package com.oxyl.NewroFactory.dto.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerDto {
	private String label;
	private String text;
	private int validAnswer;
	private int questionId;
	
	@JsonCreator
	public AnswerDto(@JsonProperty("label") String label,@JsonProperty("text") String text,
			@JsonProperty("validAnswer") int validAnswer, @JsonProperty("quesitonId") int questionId) {
		super();
		this.label = label;
		this.text = text;
		this.validAnswer = validAnswer;
		this.questionId = questionId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getValidAnswer() {
		return validAnswer;
	}
	public void setValidAnswer(int validAnswer) {
		this.validAnswer = validAnswer;
	}
	
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	@Override
	public String toString() {
		return "AnswerDto [label=" + label + ", text=" + text + ", validAnswer=" + validAnswer + "]";
	}
	
	
}
