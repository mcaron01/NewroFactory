package com.oxyl.NewroFactory.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oxyl.NewroFactory.dto.back.ChapterEntity;

public interface ChapterRepository extends JpaRepository<ChapterEntity, Integer> {

	@Query("select c from ChapterEntity c where c.parentPath = '/' order by c.name")
	public List<ChapterEntity> listParentChapter();
	
	@Query("select c from ChapterEntity c where c.parentPath LIKE :name")
	public List<ChapterEntity> listAllChildChapter(@Param("name") String name);

	@Query("select c from ChapterEntity c where c.parentPath = :name")
	public List<ChapterEntity> listChildChapter(@Param("name") String name);
}