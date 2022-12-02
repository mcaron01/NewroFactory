package com.oxyl.NewroFactory.dto.front;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oxyl.NewroFactory.validation.IsNotInDb;

public class RegisterDto {
	@IsNotInDb
	private String username;
	@NotBlank
	private String password;
	
	@JsonCreator
	public RegisterDto(@JsonProperty("username") String username,@JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
