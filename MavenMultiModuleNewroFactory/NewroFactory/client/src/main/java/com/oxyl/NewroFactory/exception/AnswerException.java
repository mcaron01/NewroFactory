package com.oxyl.NewroFactory.exception;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class AnswerException extends SQLException{
	private String message;

	public AnswerException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
