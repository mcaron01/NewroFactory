package com.oxyl.NewroFactory.exception;

@SuppressWarnings("serial")
public class UserException extends RuntimeException {
	
	private String message;

	public UserException(String message) {
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
