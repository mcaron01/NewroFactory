package com.oxyl.NewroFactory.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oxyl.NewroFactory.dto.front.LogoutDto;
import com.oxyl.NewroFactory.dto.front.RegisterDto;
import com.oxyl.NewroFactory.dto.front.UserDto;
import com.oxyl.NewroFactory.exception.InternException;
import com.oxyl.NewroFactory.exception.UserException;
import com.oxyl.NewroFactory.mapper.UserDtoMapper;
import com.oxyl.NewroFactory.mapper.UserRegisterDtoMapper;
import com.oxyl.NewroFactory.model.Intern;
import com.oxyl.NewroFactory.model.User;
import com.oxyl.NewroFactory.service.UserService;
import com.oxyl.NewroFactory.utils.JwtResponse;
import com.oxyl.NewroFactory.utils.JwtTokenUtil;

@RestController
@CrossOrigin
public class UserController {
	private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private AuthenticationManager authenticationManager;
	private JwtTokenUtil jwtTokenUtil;
	private UserService userService;
	private UserRegisterDtoMapper userRegisterDtoMapper;
	private UserDtoMapper userDtoMapper;
	
	private static final String PAGE = "page";
	private static final String NB_USERS = "nbUsers";
	private static final String SEARCH = "search";
	private static final String ORDER = "orderBy";
	private static final String ORDER_BY_DIRECTION = "orderByDirection";
	private static final String ORDER_BY_DIRECTION_DESC = "desc";
	private static final String USERNAME= "username";
	private static final String NEXT = "next";
	private static final String PREVIOUS = "previous";
	private static final String USER = "user";

	public UserController(UserService userService, AuthenticationManager authenticationManager,
			JwtTokenUtil jwtTokenUtil, UserRegisterDtoMapper userRegisterDtoMapper, UserDtoMapper userDtoMapper) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRegisterDtoMapper = userRegisterDtoMapper;
		this.userDtoMapper = userDtoMapper;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody RegisterDto registerDto) throws Exception {
		authenticate(registerDto.getUsername(), registerDto.getPassword());
		final UserDetails userDetails = userService.loadUserByUsername(registerDto.getUsername());
		User u = userService.get(registerDto.getUsername()).get();
//		if(u.isEnabled()) {
//			u.setEnabled(false);
//			this.userService.enabled(u);
//		}
//		else {
//			throw new UserException("Déja connecté");
//		}
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token, registerDto.getUsername(),
				userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0)));
	}
	
	@PostMapping("/deco")
	public ResponseEntity<String> logout(@RequestBody LogoutDto logout)throws UserException{
		User u = userService.get(logout.getUsername()).get();
		u.setEnabled(true);
		this.userService.enabled(u);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@PostMapping("/register")
	public Map<String, String> doRegister(@Valid @RequestBody RegisterDto userDto, BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		if (result.hasErrors()) {
			result.getFieldErrors().forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));
		} else {
			User user = userRegisterDtoMapper.mapUserRegisterDtoToUser(userDto);
			this.userService.register(user);
		}
		return errors;
	}

	@PatchMapping("/user/{id}")
	public Map<String, String> editUser(@Valid @RequestBody UserDto userDto, @PathVariable int id,
			BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		if (result.hasErrors()) {
			result.getFieldErrors().forEach((f -> errors.put(f.getField(), f.getDefaultMessage())));
		} else {
			User user = userDtoMapper.toUser(userDto);
			user.setId(id);
			this.userService.modify(user);
		}
		return errors;
	}

	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<Integer> delete(@PathVariable int id) throws InternException {
		this.userService.delete(id);
		return new ResponseEntity<Integer>(id, HttpStatus.OK);
	}
	
	@GetMapping("/user/dashboard")
	public List<UserDto> users(@RequestParam Map<String, String> params)throws UserException{
		Pageable page;
		List<User> users = new ArrayList<>();
		if (this.isOrderByDesc(params)) {
			page = PageRequest.of(this.page(params) - 1, this.nbUsers(params), Sort.by(this.order(params)).descending());
		} else {
			page = PageRequest.of(this.page(params) - 1, this.nbUsers(params), Sort.by(this.order(params)));
		}
		String searchInput = this.search(params).trim();
		if (searchInput == null || searchInput.isEmpty()) {
			users = this.userService.list(page,this.user(params));
		} else {
			String[] searchInputs = searchInput.split(" ");
				users = this.userService.getListByName(page, searchInputs[0]);
		}
		return users.stream().map(localUser->this.userDtoMapper.toUserDto(localUser)).toList();
	}
	
	public int nbUsers(Map<String, String> params) {
		int nbInterns = 10;
		if (params.containsKey(NB_USERS)) {
			nbInterns = Integer.parseInt(params.get(NB_USERS));
		}
		return nbInterns;
	}

	public int page(Map<String, String> params) {
		int page = 1;
		if (params.containsKey(PAGE)) {
			page = Integer.parseInt(params.get(PAGE));
		}
		if (params.containsKey(NEXT)) {
			page = next(page);
		} else if (params.containsKey(PREVIOUS)) {
			page = previous(page);
		}
		return page;
	}

	public String order(Map<String, String> params) {
		String order = USERNAME;
		if (params.containsKey(ORDER)) {
			order = params.get(ORDER);
		}
		return order;
	}
	
	public String user(Map<String,String> params) {
		String user = "";
		if(params.containsKey(USER)) {
			user = params.get(USER);
		}
		return user;
	}
	
	public String search(Map<String, String> params) {
		String search = "";
		if (params.containsKey(SEARCH)) {
			search = params.get(SEARCH);
		}
		return search;
	}

	public boolean isOrderByDesc(Map<String, String> params) {
		boolean orderByDesc = false;
		if (params.containsKey(ORDER_BY_DIRECTION) && params.get(ORDER_BY_DIRECTION).equals(ORDER_BY_DIRECTION_DESC)) {
			orderByDesc = true;
		}
		return orderByDesc;
	}

	public int next(int page) {
		return page + 5;
	}

	public int previous(int page) {
		if (page < 6) {
			page = 1;
		} else {
			page -= 5;
		}
		return page;
	}
	
	@GetMapping("/user/{id}")
	public UserDto user(@PathVariable int id) throws UserException{
		Optional<User> optionalUser = this.userService.getById(id);
		if (optionalUser.isEmpty()) {
			throw new UserException("user not found");
		}
		return this.userDtoMapper.toUserDto(optionalUser.get());
	}
	
	@GetMapping("/user/count")
	public long count()throws UserException{
		return this.userService.count();
	}
}