package com.oxyl.NewroFactory.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;


import com.oxyl.NewroFactory.dto.back.InternEntity;

public interface InternRepository extends JpaRepository<InternEntity, Integer> {
	
	@Query("SELECT I FROM InternEntity AS I INNER JOIN PromotionEntity as P ON I.promotion = P.id WHERE P.name LIKE :promotion AND (I.lastName LIKE :lastName "
			+ "AND I.firstName LIKE :firstName OR I.firstName LIKE :lastName AND I.lastName LIKE :firstName)")
	List<InternEntity> findAllInternsSearchByCompleteNameAndPromotion(@Param("firstName") String firstname, @Param("lastName") String lastname, @Param("promotion") String promotion, Pageable pageable);

	@Query("SELECT I FROM InternEntity AS I INNER JOIN PromotionEntity as P ON I.promotion = P.id WHERE I.lastName LIKE :lastName "
			+ "AND I.firstName LIKE :firstName OR I.firstName LIKE :lastName AND I.lastName LIKE :firstName")
	List<InternEntity> findAllInternsSearchByCompleteName(@Param("firstName") String firstname, @Param("lastName") String lastname, Pageable pageable);
	
	@Query("SELECT I FROM InternEntity AS I INNER JOIN PromotionEntity as P ON I.promotion = P.id WHERE P.name LIKE :promotion AND (I.lastName LIKE :name "
			+ "OR I.firstName LIKE :name)")
	List<InternEntity> findAllInternByNameAndPromotion(@Param("name") String name, @Param("promotion") String promotion, Pageable pageable);

	@Query("SELECT I FROM InternEntity AS I INNER JOIN PromotionEntity as P ON I.promotion = P.id WHERE I.lastName LIKE :name "
			+ "OR I.firstName LIKE :name")
	List<InternEntity> findAllInternByName(@Param("name") String name, Pageable pageable);
	
	@Query("SELECT I FROM InternEntity AS I INNER JOIN PromotionEntity as P ON I.promotion = P.id WHERE P.name LIKE :promotionName")
	List<InternEntity> findAllInternByPromotionName(@Param("promotionName") String promotionName, Pageable pageable);

	@Query("SELECT COUNT(I) FROM InternEntity AS I INNER JOIN PromotionEntity as P ON I.promotion = P.id WHERE P.name LIKE :promotion AND (I.lastName LIKE :name OR I.firstName LIKE :name)")
	long countAllInternByNameAndPromotion(@Param("name") String name, @Param("promotion") String promotion);

	@Query("SELECT COUNT(I) FROM InternEntity AS I INNER JOIN PromotionEntity as P ON I.promotion = P.id WHERE P.name LIKE :promotion AND (I.lastName LIKE :lastName "
			+ "AND I.firstName LIKE :firstName OR I.firstName LIKE :lastName AND I.lastName LIKE :firstName)")
	long countAllInternByCompleteNameAndPromotion(@Param("firstName") String firstname, @Param("lastName") String lastname, @Param("promotion") String promotion);

	@Query("SELECT COUNT(I) FROM InternEntity AS I WHERE I.lastName LIKE :name OR I.firstName LIKE :name")
	long countAllInternByName(@Param("name") String name);

	@Query("SELECT COUNT(I) FROM InternEntity AS I INNER JOIN PromotionEntity as P ON I.promotion = P.id WHERE I.lastName LIKE :lastName "
			+ "AND I.firstName LIKE :firstName OR I.firstName LIKE :lastName AND I.lastName LIKE :firstName")
	long countAllInternByCompleteName(@Param("firstName") String firstname, @Param("lastName") String lastname);
	
	@Query("SELECT COUNT(I) FROM InternEntity AS I INNER JOIN PromotionEntity as P ON I.promotion = P.id WHERE P.name LIKE :promotionName")
	long countAllInternByPromotionName(@Param("promotionName") String promotionName);
}