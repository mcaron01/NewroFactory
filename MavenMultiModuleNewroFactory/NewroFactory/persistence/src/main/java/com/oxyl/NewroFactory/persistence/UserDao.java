package com.oxyl.NewroFactory.persistence;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oxyl.NewroFactory.dto.back.InternEntity;
import com.oxyl.NewroFactory.dto.back.UserEntity;
import com.oxyl.NewroFactory.exception.InternException;
import com.oxyl.NewroFactory.exception.UserException;
import com.oxyl.NewroFactory.mapper.UserMapper;
import com.oxyl.NewroFactory.model.Intern;
import com.oxyl.NewroFactory.model.User;
import com.oxyl.NewroFactory.persistence.jpa.UserRepository;

@Repository
@Transactional
public class UserDao {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	private UserMapper userMapper;
	private UserRepository userRepository;
	
	@Autowired
	public UserDao(UserMapper userMapper, UserRepository userRepository) {
		this.userMapper = userMapper;
		this.userRepository = userRepository;
	}
	
	public Optional<User> authenticate(String username) throws UserException {
		
		try {
			Optional<UserEntity> userDto = userRepository.findByUsername(username);
			
			if (userDto.isEmpty()) {
				return Optional.empty();
			} else {
	
			Optional<User> user = Optional.ofNullable(this.userMapper.mapUserDtoToUser(userDto.get()));
			LOGGER.info("L'utilisateur a bien été recupéré");
			return user;
			}
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour afficher les détails d'un stagiaire ne fonctionne pas", e);
			throw new UserException("Désolé, il est impossible d'acceder aux utilisateurs disponibles");
		}
	}
	
	public void create(User user) throws UserException {
		
		try {
			UserEntity userDto = userMapper.mapUserToUserDto(user);
			userRepository.save(userDto);
			LOGGER.info("La requête pour créer un utilisateur fonctionne");
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour créer un utilisateur ne fonctionne pas", e);
			throw new UserException("Désole, nous n'avons pas réussi à créer l'utilisateur");
		}
	}
	
	public void modify(User user) throws UserException {
		try {
			UserEntity userEntity = this.userMapper.mapUserToUserDto(user);
			this.userRepository.modifyRole(userEntity.getRole(),userEntity.getId());
		}catch(DataAccessException e) {
			LOGGER.error("La requête pour modifier un utilisateur ne fonctionne pas",e);
			throw new UserException("Désole, nous n'avons pas réussi à modifier l'utilisateur");
		}
	}
	
	public void delete(int id) throws UserException {
		try {
			userRepository.deleteById(id);
			LOGGER.info("La requête pour supprimer un utilisateur fonctionne");
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour supprimer un utilisateur ne fonctionne pas", e);
			throw new UserException("Désolé, nous n'avons pas réussi a supprimer cet utilisateur");
		}
	}
	
	public List<User> list(Pageable page, String user) throws UserException {
		try {
			List<UserEntity> listEntity = userRepository.findWithoutUser(user, page);
			return this.userMapper.toListUser(listEntity);
		}catch(DataAccessException e) {
			LOGGER.error("La requête pour récupérer des utiisateurs ne fonctionne pas", e);
			throw new UserException("Désole, nous n'avons pas réussi à récupérer les utilisateurs");
		}
	}
	
	public Optional<User> getById(int id) throws UserException {
		try {
			Optional<UserEntity> user = this.userRepository.findById(id);
			if(user.isEmpty()) {
				return Optional.empty();
			}
			return user.map(userEntity->this.userMapper.mapUserDtoToUser(userEntity));
			
		}catch(DataAccessException e) {
			LOGGER.error("La requête pour récupérer un utilisateur ne fonctionne pas", e);
			throw new UserException("Désole, nous n'avons pas réussi à récupérer l'utilisateur");
		}
	}
	
	public List<User> getUsersByUsername(Pageable page, String name) throws UserException {

		try {
			List<UserEntity> listUserEntity = userRepository.findAllUsersByName(name, page);
			List<User> listUser = userMapper.toListUser(listUserEntity);
			LOGGER.info("La requête pour obtenir une liste de stagiaire avec nom ou prénom fonctionne");
			return listUser;
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour obtenir une liste d'utilisateurs avec l'identifiant ne fonctionne pas",
					e);
			throw new UserException(
					"Désolé, il est impossible d'obtenir la liste d'utilisateurs avec cet identifiant");
		}
	}
	public List<User> findAll()throws UserException{
		try {
			List<UserEntity> list = userRepository.findAll();
			return this.userMapper.toListUser(list);
		}catch(DataAccessException e) {
			LOGGER.error("La requête pour obtenir une liste d'utilisateurs avec l'identifiant ne fonctionne pas",e);
			throw new UserException("Problème lors de la récupération de la liste des utilisateurs");
		}
	}
	
	public long count() throws UserException{
		try {
			return this.userRepository.count();
		}catch(DataAccessException e) {
			throw new UserException("Impossible de récupérer le nombre total de stagiaires");
		}
	}
	
	public void enabled(User user)throws UserException{
		try {
			this.userRepository.enabled(user.isEnabled(), user.getId());
		}catch(DataAccessException e) {
			throw new UserException("Problème lors de la modification de l'accessibilité");
		}
	}
}
