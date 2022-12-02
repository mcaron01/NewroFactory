package com.oxyl.NewroFactory.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.front.RegisterDto;
import com.oxyl.NewroFactory.model.Role;
import com.oxyl.NewroFactory.model.User;

@Component
public class UserRegisterDtoMapper {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserRegisterDtoMapper.class);
	private PasswordEncoder encoder;

	@Autowired
	public UserRegisterDtoMapper(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public User mapUserRegisterDtoToUser(RegisterDto registerDto) {
		User user = new User.UserBuilder().username(registerDto.getUsername())
				.password(encoder.encode(registerDto.getPassword())).enabled(true).role(Role.USER).build();
		LOGGER.info("Le mapping d'un UserRegister a un User est passé");
		return user;
	}
	
	public RegisterDto toDto(User user) {
		return new RegisterDto(user.getUsername(), user.getPassword());
	}

}
