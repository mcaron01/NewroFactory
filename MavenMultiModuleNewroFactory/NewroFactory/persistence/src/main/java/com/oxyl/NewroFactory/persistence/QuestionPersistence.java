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

import com.oxyl.NewroFactory.dto.back.QuestionEntity;
import com.oxyl.NewroFactory.exception.QuestionException;
import com.oxyl.NewroFactory.mapper.QuestionMapper;
import com.oxyl.NewroFactory.model.Question;
import com.oxyl.NewroFactory.persistence.jpa.QuestionRepository;

@Repository
public class QuestionPersistence {

	private final static Logger LOGGER = LoggerFactory.getLogger(QuestionPersistence.class);

	private QuestionMapper questionMapper;
	private QuestionRepository questionRepository;

	@Autowired
	public QuestionPersistence(QuestionMapper questionMapper, QuestionRepository questionRepository) {
		this.questionMapper = questionMapper;
		this.questionRepository = questionRepository;
	}

	public Optional<Question> getQuestionById(int id) throws QuestionException {
		try {
			QuestionEntity questionEntity = questionRepository.findById(id)
					.orElseThrow(() -> new QuestionException("Désolé, cette question n'a pas été trouvée"));
			Question question = questionMapper.mapQuestionDTOToQuestion(questionEntity);
			LOGGER.info("La requête pour obtenir les détails d'une question fonctionne");
			return Optional.ofNullable(question);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("Les informations de la question n'ont pas été recupérées", e);
			return Optional.empty();
		} catch (DataAccessException e) {
			LOGGER.error("Exception due a la requete", e);
			throw new QuestionException("Désolé, il est impossible d'afficher les détails de la question demandée");
		}
	}

	public List<Question> getListOfQuestion(Pageable questionPage) throws QuestionException {
		try {
			List<QuestionEntity> listQuestionEntity = questionRepository.findAll(questionPage).toList();
			List<Question> listQuestion = questionMapper.mapListQuestionDtoToListQuestion(listQuestionEntity);
			LOGGER.info("La requête pour obtenir une page de question fonctionne");
			return listQuestion;
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour lister les questions dans une page ne fonctionne pas", e);
			throw new QuestionException("Désolé, il est impossible d'afficher la page de questions demandée");
		}
	}

	public List<Question> getQuestionsByChapter(int chapterId, Pageable questionPage) throws QuestionException {
		try {
			List<QuestionEntity> questions = this.questionRepository.getQuestionsByChapter(chapterId, questionPage);
			return questions.stream().map(q->this.questionMapper.mapQuestionDTOToQuestion(q)).toList();
		} catch(DataAccessException e) {
			LOGGER.error("Problème de récupération des questions",e);
			throw new QuestionException("Problème de récupération des questions");
		}
	}
	
	public long count() throws QuestionException {
		try {
			return questionRepository.count();
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour compter le nombre d'éléments dans la table question ne fonctionne pas", e);
			throw new QuestionException("Désolé, nous n'avons pas réussi a compter le nombre de questions");
		}
	}
	
	public long countQuestionsByChapter(int chapterId) throws QuestionException {
		try {
			return questionRepository.countQuestionsByChapter(chapterId);
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour compter le nombre d'éléments dans la table question ne fonctionne pas", e);
			throw new QuestionException("Désolé, nous n'avons pas réussi a compter le nombre de questions");
		}
	}

	public void create(Question question) throws QuestionException {

		try {
			questionRepository.save(questionMapper.mapQuestionToQuestionDto(question));
			LOGGER.info("La requête pour créer une question fonctionne");
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour créer une question ne fonctionne pas", e);
			throw new QuestionException("Désolé, nous n'avons pas réussi à créer la question");
		}
	}

	public void edit(Question question) throws QuestionException {

		try {
			questionRepository.save(questionMapper.mapQuestionToQuestionDto(question));
			LOGGER.info("La requête pour modifier une question fonctionne");
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour modifier une question ne fonctionne pas", e);
			throw new QuestionException("Désolé, nous n'avons pas pu modifer cete question");
		}
	}
	
	public void delete(int questionId) throws QuestionException {
		try {
			questionRepository.deleteById(questionId);

		} catch (DataAccessException e) {
			LOGGER.error("Exception due a la requete", e);
			throw new QuestionException("Désolé, il est impossible de supprimer la question");
		}
	}
	
	public List<Question> getQuestionParentChapter(Pageable page, List<Integer> list) throws QuestionException{
		try {
		List<QuestionEntity> questionsEntity = this.questionRepository.getQuestionsByParentChapter(list, page);
		List<Question> questions = this.questionMapper.mapListQuestionDtoToListQuestion(questionsEntity);
		return questions;
		}catch(DataAccessException e){
			throw new QuestionException("Problème lors de la récupération des questions");
		}
	}
	
	public long getCountByParentChapter(List<Integer> list) throws QuestionException {
		System.out.println(list);
		try {
			long count = 0;
		for(int id : list) {
			count += this.countQuestionsByChapter(id);
		}
		return count;
		}
		catch(DataAccessException e) {
			throw new QuestionException("Impossible de récupérer le nombre de questions par chapitre parent");
		}
	}

}
