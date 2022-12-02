package com.oxyl.NewroFactory.dto.front;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oxyl.NewroFactory.model.Role;

public class UserDto {
	private int id;
	private String username;
	private String password;
	private Role role;
	
	@JsonCreator
	public UserDto(@JsonProperty("id") int id, @JsonProperty("username") String username,@JsonProperty("role") Role role) {
		this.id = id;
		this.username = username;
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "UserDto [username=" + username + ", role=" + role + "]";
	}
	
	
}
