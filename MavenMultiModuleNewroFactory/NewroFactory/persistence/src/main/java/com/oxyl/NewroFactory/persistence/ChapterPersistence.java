package com.oxyl.NewroFactory.persistence;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.oxyl.NewroFactory.dto.back.ChapterEntity;
import com.oxyl.NewroFactory.dto.back.QuestionEntity;
import com.oxyl.NewroFactory.exception.ChapterException;
import com.oxyl.NewroFactory.exception.PromotionException;
import com.oxyl.NewroFactory.exception.QuestionException;
import com.oxyl.NewroFactory.mapper.ChapterMapper;
import com.oxyl.NewroFactory.model.Chapter;
import com.oxyl.NewroFactory.model.Question;
import com.oxyl.NewroFactory.persistence.jpa.ChapterRepository;

@Repository
public class ChapterPersistence {

	private final static Logger LOGGER = LoggerFactory.getLogger(ChapterPersistence.class);

	private ChapterMapper chapterMapper;
	private ChapterRepository chapterRepository;

	@Autowired
	public ChapterPersistence(ChapterMapper chapterMapper, ChapterRepository chapterRepository) {
		this.chapterMapper = chapterMapper;
		this.chapterRepository = chapterRepository;
	}

	public List<Chapter> getListOfChapter(Pageable chapterPage) throws ChapterException {
		try {
			List<ChapterEntity> listChapterEntity = chapterRepository.findAll(chapterPage).toList();
			List<Chapter> listChapter = chapterMapper.mapListChapterDtoToListChapter(listChapterEntity);
			LOGGER.info("La requête pour obtenir une page de chapitre fonctionne");
			return listChapter;
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour lister les chapitres dans une page ne fonctionne pas", e);
			throw new ChapterException("Désolé, il est impossible d'afficher la page de chapitre demandée");
		}
	}

	public Chapter getChapterById(int id) throws ChapterException {
		try {
			ChapterEntity chapterEntity = chapterRepository.findById(id)
					.orElseThrow(() -> new ChapterException("Désolé, ce chapitre n'a pas été trouvé"));
			Chapter chapter = chapterMapper.mapChapterDtoToChapter(chapterEntity);
			LOGGER.info("La requête pour obtenir les détails d'un chapitre fonctionne");
			return chapter;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("La promotion n'existe pas", e);
			throw new ChapterException("Le chapitre que vous renseigez n'existe pas");
		} catch (DataAccessException e) {
			LOGGER.error("Erreur dans la récupération du nom de la promotion", e);
			throw new ChapterException("Désolé, le nom du chapitre n'a pas été bien récupéré");
		}
	}

	public List<Chapter> getListOfAllChapter() throws ChapterException {
		try {
			List<ChapterEntity> listChapterEntity = chapterRepository.findAll();
			List<Chapter> listChapter = chapterMapper.mapListChapterDtoToListChapter(listChapterEntity);
			LOGGER.info("La requête pour obtenir une page de chapitre fonctionne");
			return listChapter;
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour lister les chapitres dans une page ne fonctionne pas", e);
			throw new ChapterException("Désolé, il est impossible d'afficher la page de chapitre demandée");
		}
	}

	public long count() throws ChapterException {

		try {
			return chapterRepository.count();
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour compter le nombre d'éléments dans la table chapter ne fonctionne pas", e);
			throw new ChapterException("Désolé, nous n'avons pas réussi a compter le nombre de chapitres");
		}
	}
	
	public List<Chapter> listParentChapters() throws ChapterException{
		try {
			List<ChapterEntity> list = this.chapterRepository.listParentChapter();
			return list.stream().map(c->this.chapterMapper.mapChapterDtoToChapter(c)).toList();
		}catch(DataAccessException e) {
			LOGGER.error("Problème lors de la récupération des chapitres principaux");
			throw new ChapterException("Désolé, nous n'avons pas réussi à récupérer la liste des chapitres");
		}
	}


	public List<Chapter> listChildChapters(String name, String parentPath) throws ChapterException{
		try {
			List<ChapterEntity> list = this.chapterRepository.listChildChapter(parentPath+name+"/");
			return list.stream().map(c->this.chapterMapper.mapChapterDtoToChapter(c)).toList();
		}catch(DataAccessException e) {
			LOGGER.error("Problème lors de la récupération des chapitres");
			throw new ChapterException("Désolé, nous n'avons pas réussi à récupérer la liste des chapitres");
		}
	}

	public List<Chapter> listAllChildChapters(String name, String parentPath) throws ChapterException{
		try {
			List<ChapterEntity> list = this.chapterRepository.listAllChildChapter(parentPath+name+"/%");
			return list.stream().map(c->this.chapterMapper.mapChapterDtoToChapter(c)).toList();
		}catch(DataAccessException e) {
			LOGGER.error("Problème lors de la récupération des chapitres");
			throw new ChapterException("Désolé, nous n'avons pas réussi à récupérer la liste des chapitres");
		}
	}
	
	public void create(Chapter chapter) throws ChapterException{
		try {
		this.chapterRepository.save(this.chapterMapper.toEntity(chapter));
		}catch(DataAccessException e) {
			LOGGER.error("Problème lors de la création d'un chapitre");
			throw new ChapterException("Désolé, nous n'avons pas réussi à créer un chapitre");
		}
	}
	
	public void delete(int id) throws ChapterException {
		try {
			this.chapterRepository.deleteById(id);
		}catch(DataAccessException e) {
			LOGGER.error("Problème lors de la suppression d'un chapitre");
			throw new ChapterException("Désolé, nous n'avons pas réussi à supprimer un chapitre");
		}
	}
	
	public Optional<Chapter> getById(int id) throws ChapterException {
		try {
			ChapterEntity chapterEntity = this.chapterRepository.findById(id)
					.orElseThrow(() -> new ChapterException("Désolé, ce chapitre n'a pas été trouvée"));
			Chapter chapter = this.chapterMapper.mapChapterDtoToChapter(chapterEntity);
			LOGGER.info("La requête pour obtenir les détails d'un chapitre fonctionne");
			return Optional.ofNullable(chapter);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("Les informations du chapitre n'ont pas été recupérées", e);
			return Optional.empty();
		} catch (DataAccessException e) {
			LOGGER.error("Exception due a la requête", e);
			throw new ChapterException("Désolé, il est impossible d'afficher les détails du chapitre demandée");
		}
	}
}
