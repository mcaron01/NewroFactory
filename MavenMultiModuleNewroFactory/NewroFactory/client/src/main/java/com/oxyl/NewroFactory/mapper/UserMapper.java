package com.oxyl.NewroFactory.mapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.back.UserEntity;
import com.oxyl.NewroFactory.model.User;

@Component
public class UserMapper {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserMapper.class);

	public User mapUserDtoToUser(UserEntity userDto) {
		User user = new User.UserBuilder().id(userDto.getId()).username(userDto.getUsername()).password(userDto.getPassword())
				.role(userDto.getRole()).enabled(userDto.getEnabled()).build();
		LOGGER.info("Le mapping d'un dto User en User est passé");
		return user;
	}
	
	public UserEntity mapUserToUserDto(User user) {
		UserEntity userDto = new UserEntity(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.isEnabled());
		LOGGER.info("Le mapping d'un User en dto User est passé");
		return userDto;
	}
	
	public List<User> toListUser(List<UserEntity> list){
		return list.stream().map(e->this.mapUserDtoToUser(e)).toList();
	}

}
