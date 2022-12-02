package com.oxyl.NewroFactory.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oxyl.NewroFactory.exception.InternException;
import com.oxyl.NewroFactory.exception.UserException;
import com.oxyl.NewroFactory.model.Intern;
import com.oxyl.NewroFactory.model.User;
import com.oxyl.NewroFactory.persistence.UserDao;

@Service
public class UserService implements UserDetailsService {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	private final static String USER_NOT_FOUND_MSG = "User with username %s not found";

	private UserDao userDao;

	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UserException {
		UserDetails userDetails = userDao.authenticate(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
		LOGGER.info("Les détails de l'utilisateur sont bien recupérés et envoyés");
		return userDetails;
	}
	
	public Optional<User> get(String username){
		return this.userDao.authenticate(username);
	}

	public void register(User user) throws UserException {
		userDao.create(user);
		LOGGER.info("L'utilisateur est bien créé !");
	}
	
	public List<User> list(Pageable page, String user){
		return this.userDao.list(page,user);
	}
	
	public void modify(User user) throws UserException {
		this.userDao.modify(user);
		LOGGER.info("L'utilisateur est bien modifié !");
	}
	
	public void delete(int id) throws UserException {
		this.userDao.delete(id);
		LOGGER.info("L'utilisateur est bien supprimé !");
	}
	
	public Optional<User> getById(int id) throws UserException{
		return this.userDao.getById(id);
	}
	
	public List<User> getListByName(Pageable page, String name) throws UserException {
		List<User> list = userDao.getUsersByUsername(page, name);
		LOGGER.info("La liste de stagiaire a bien été récupérée");
		return list;
	}
	
	public List<User> findAll()throws UserException{
		return this.userDao.findAll();
	}
	
	public long count() throws UserException{
		return this.userDao.count();
	}
	
	public void enabled(User user)throws UserException{
		this.userDao.enabled(user);
	}

}
