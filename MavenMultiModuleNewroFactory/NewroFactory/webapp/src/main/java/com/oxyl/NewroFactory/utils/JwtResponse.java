package com.oxyl.NewroFactory.utils;

import com.oxyl.NewroFactory.dto.front.RegisterDto;
import com.oxyl.NewroFactory.model.User;

public class JwtResponse {
	private final String jwttoken;
	private final String userDto ;
	private final String role ;

    public JwtResponse(String jwttoken, String userDto, String role) {
        this.jwttoken = jwttoken;
        this.userDto = userDto ;
        this.role = role ;
    }

	public String getJwttoken() {
		return jwttoken;
	}

	public String getUsername() {
		return userDto;
	}

	public String getRole() {
		return role;
	}

}
