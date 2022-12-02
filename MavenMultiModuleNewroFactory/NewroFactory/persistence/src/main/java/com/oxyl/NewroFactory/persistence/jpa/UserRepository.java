package com.oxyl.NewroFactory.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oxyl.NewroFactory.dto.back.InternEntity;
import com.oxyl.NewroFactory.dto.back.UserEntity;
import com.oxyl.NewroFactory.model.Role;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	public Optional<UserEntity> findByUsername(String username);
	
	@Modifying
	@Query("update UserEntity u set u.role = :role where u.id = :id")
	public void modifyRole(@Param("role") Role role,@Param("id") int id);
	
	@Query("SELECT U FROM UserEntity AS U WHERE U.username LIKE :name ")
	List<UserEntity> findAllUsersByName(@Param("name") String name, Pageable pageable);
	
	@Modifying
	@Query("update UserEntity u set u.enabled = :enabled where u.id = :id")
	public void enabled(@Param("enabled") boolean enabled,@Param("id") int id);
	
	@Query("select u from UserEntity u where u.username != :user")
	List<UserEntity> findWithoutUser(@Param("user") String user, Pageable pageable);
}
