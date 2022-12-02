package com.oxyl.NewroFactory.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;

import com.oxyl.NewroFactory.dto.front.RegisterDto;
import com.oxyl.NewroFactory.mapper.UserMapper;
import com.oxyl.NewroFactory.mapper.UserRegisterDtoMapper;
import com.oxyl.NewroFactory.model.User;
import com.oxyl.NewroFactory.service.UserService;

public class UsernameValidator implements ConstraintValidator<IsNotInDb, String>{
	UserService userService;
	UserRegisterDtoMapper userRegisterDtoMapper;
	private final static Logger LOGGER = LoggerFactory.getLogger(UsernameValidator.class);
	public UsernameValidator(UserService userService, UserRegisterDtoMapper userRegisterDtoMapper) {
		this.userService = userService;
		this.userRegisterDtoMapper = userRegisterDtoMapper;
	}
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
			List<User> users = this.userService.findAll();
			for (User user : users) {
				if(value.equals(this.userRegisterDtoMapper.toDto(user).getUsername())){
					LOGGER.error("Username déjà existant");
					return false;
				}
			}
		return true;
	}
	
}
