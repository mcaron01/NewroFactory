package com.oxyl.NewroFactory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oxyl.NewroFactory.exception.ChapterException;
import com.oxyl.NewroFactory.model.Chapter;
import com.oxyl.NewroFactory.persistence.ChapterPersistence;

@Service
@Transactional
public class ChapterService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ChapterService.class);

	private ChapterPersistence chapterPersistence;

	@Autowired
	public ChapterService(ChapterPersistence chapterPersistence) {
		this.chapterPersistence = chapterPersistence;
	}

	public void printList(List<Chapter> listChapter) {
		for (Chapter p : listChapter)
			System.out.println(p);
		LOGGER.info("La liste des chapters a bien été affiché");
	}

	public boolean isInListChapter(int id) throws ChapterException {
		List<Chapter> listChapter = chapterPersistence.getListOfAllChapter();
		boolean isInListOfPromo = listChapter.stream().filter(x -> Objects.equals(x.getId(), id)).count() > 0;
		LOGGER.info("Le test de savoir si la chapter existe est passé");
		return isInListOfPromo;
	}

	public Chapter createInstanceChapterWithId(int id) throws ChapterException {
		Chapter chapter = chapterPersistence.getChapterById(id);
		LOGGER.info("L'instance de chapter avec id a bien été créée");
		return chapter;
	}

	public List<Chapter> getAll() throws ChapterException {
		List<Chapter> listChapter = chapterPersistence.getListOfAllChapter();
		LOGGER.info("La liste des chapters a bien été recupéré");
		return listChapter;
	}

	public List<Chapter> listParentChapter() throws ChapterException{
		return this.chapterPersistence.listParentChapters();
	}
	
	public List<Chapter> listChildChapter(String name, String parentPath) throws ChapterException{
		return this.chapterPersistence.listChildChapters(name, parentPath);
	}

	public List<Chapter> listAllChildChapter(String name, String parentPath) throws ChapterException{
		return this.chapterPersistence.listAllChildChapters(name, parentPath);
	}
	
	public void create(Chapter chapter) throws ChapterException {
		this.chapterPersistence.create(chapter);
	}
	
	public void delete(int id)throws ChapterException{
		this.chapterPersistence.delete(id);
	}
	
	public Optional<Chapter> getById(int id) throws ChapterException {
		return this.chapterPersistence.getById(id);
	}

}
