package com.oxyl.NewroFactory.persistence.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oxyl.NewroFactory.dto.back.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
	
	@Query("SELECT Q FROM QuestionEntity Q WHERE Q.chapter.id = :id")
	public List<QuestionEntity> getQuestionsByChapter(@Param("id") int id, Pageable pageable);

	@Query("SELECT COUNT(Q) FROM QuestionEntity Q WHERE Q.chapter.id = :id")
	public long countQuestionsByChapter(@Param("id") int id);
	
	@Query("select q from QuestionEntity q where q.chapter.id in (:list)")
	List<QuestionEntity> getQuestionsByParentChapter(@Param("list")List<Integer> list, Pageable pageable);
}