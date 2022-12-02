package com.oxyl.NewroFactory.mapper;

import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.front.UserDto;
import com.oxyl.NewroFactory.model.User;

@Component
public class UserDtoMapper {

	public UserDtoMapper() {
	}
	
	public User toUser(UserDto userDto) {
		return new User.UserBuilder().id(userDto.getId()).username(userDto.getUsername()).role(userDto.getRole()).build();
	}
	
	public UserDto toUserDto(User user) {
		return new UserDto(user.getId(),user.getUsername(),user.getRole());
	}
}
