package com.oxyl.NewroFactory.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oxyl.NewroFactory.dto.back.AnswerEntity;


public interface AnswerRepository extends JpaRepository<AnswerEntity, Integer>{
	@Query("select a from AnswerEntity a where a.question.id = :id")
	public List<AnswerEntity> getAnswers(@Param("id") int id);
}
