package com.oxyl.NewroFactory.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oxyl.NewroFactory.exception.AnswerException;
import com.oxyl.NewroFactory.exception.QuestionException;
import com.oxyl.NewroFactory.model.Question;
import com.oxyl.NewroFactory.persistence.AnswerPersistence;
import com.oxyl.NewroFactory.persistence.QuestionPersistence;

@Service
public class QuestionService {

	private final static Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);

	private QuestionPersistence questionPersistence;
	private AnswerPersistence answerPersistence;

	@Autowired
	public QuestionService(QuestionPersistence questionPersistence, AnswerPersistence answerPersistence) {
		this.questionPersistence = questionPersistence;
		this.answerPersistence = answerPersistence;
	}

	public void printQuestion(Question question) {
		System.out.println(question.toString());
		LOGGER.info("La question a bien été affichée");
	}

	@Transactional
	public void create(Question question) throws QuestionException {
		questionPersistence.create(question);
		LOGGER.info("La question a bien été ajoutée a la BDD");
	}

	@Transactional
	public void edit(int id, Question question) throws QuestionException {
		question.setId(id);
		questionPersistence.edit(question);
		LOGGER.info("La question a bien été modifiée dans la BDD");
	}

	@Transactional
	public void delete(int questionId) throws AnswerException, QuestionException {
		answerPersistence.delete(questionId);
		questionPersistence.delete(questionId);
		LOGGER.info("La question a bien été supprimée");
	}

	public List<Question> getListQuestion(Pageable questionPage) throws QuestionException {
		List<Question> listQuestion = questionPersistence.getListOfQuestion(questionPage);
		LOGGER.info("La liste de questions a bien été récupérée");
		return listQuestion;
	}

	public List<Question> getQuestionsByChapter(int chapterId, Pageable questionPage) throws QuestionException {
		return questionPersistence.getQuestionsByChapter(chapterId, questionPage);
	}
	
	public long getNbQuestion(String searchInput) throws QuestionException {
		long count = questionPersistence.count(/*searchInput*/);
		LOGGER.info("Le nombre de questions dans la base a bien été récupéré");
		return count;
	}
	
	public long getNbQuestionByChapter(int chapterId) throws QuestionException {
		long count = questionPersistence.countQuestionsByChapter(chapterId);
		LOGGER.info("Le nombre de questions dans la base a bien été récupéré");
		return count;
	}

	public Optional<Question> getById(int idQuestion) throws QuestionException {
		return questionPersistence.getQuestionById(idQuestion);
	}
	
	public List<Question> getQuestionParentChapter(List<Integer> list, Pageable page) throws QuestionException{
		return this.questionPersistence.getQuestionParentChapter(page, list);
	}
	
	public long countByChapterParent(List<Integer> list) throws QuestionException {
		return this.questionPersistence.getCountByParentChapter(list);
	}

}
