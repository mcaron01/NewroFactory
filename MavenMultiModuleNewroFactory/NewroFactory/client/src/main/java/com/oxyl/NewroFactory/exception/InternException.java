package com.oxyl.NewroFactory.exception;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class InternException extends SQLException {
	private String message;

	public InternException(String s) {
		this.message = s;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
