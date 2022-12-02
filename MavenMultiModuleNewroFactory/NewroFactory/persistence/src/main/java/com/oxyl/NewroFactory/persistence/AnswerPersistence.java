package com.oxyl.NewroFactory.persistence;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.oxyl.NewroFactory.dto.back.AnswerEntity;
import com.oxyl.NewroFactory.exception.AnswerException;
import com.oxyl.NewroFactory.mapper.AnswerMapper;
import com.oxyl.NewroFactory.model.Answer;
import com.oxyl.NewroFactory.model.Question;
import com.oxyl.NewroFactory.persistence.jpa.AnswerRepository;

@Repository
public class AnswerPersistence {

	private final static Logger LOGGER = LoggerFactory.getLogger(AnswerPersistence.class);

	private AnswerRepository answerRepository;
	private AnswerMapper answerMapper;
	@Autowired
	public AnswerPersistence(AnswerRepository answerRepository, AnswerMapper answerMapper) {
		this.answerRepository = answerRepository;
		this.answerMapper = answerMapper;
	}
	
	public List<Answer> getAnswers(int questionId) throws AnswerException {
		try {
		List<AnswerEntity> answers = this.answerRepository.getAnswers(questionId);
		return answers.stream().map(a->this.answerMapper.toAnswer(a)).toList();
		}catch(DataAccessException e) {
			LOGGER.error("Problème de récupération des réponses",e);
			throw new AnswerException("Problème de récupération des réponses");
		}
	}
	
	public void delete(int id) throws AnswerException {
		try {
			this.answerRepository.deleteById(id);
		}catch(DataAccessException e) {
			LOGGER.error("Problème lors de la suppression de réponses",e);
			throw new AnswerException("Problème lors de la suppression de réponses");
		}
	}
	
	public void create(Answer answer) throws AnswerException{
		try {
			this.answerRepository.save(this.answerMapper.toEntity(answer));
		}catch(DataAccessException e) {
			LOGGER.error("Problème lors de la création d'une réponse",e);
			throw new AnswerException("Problème lors de la création d'une réponse");
		}
	}
	
	public void modify(Answer answer) throws AnswerException{
		try {
			this.answerRepository.save(this.answerMapper.toEntity(answer));
		}catch(DataAccessException e) {
			LOGGER.error("Problème lors de la modification d'une réponse",e);
			throw new AnswerException("Problème lors de la modification d'une réponse");
		}
	}
	
	public Optional<Answer> getById(int id) throws AnswerException{
		try {
		Optional<AnswerEntity> answer = this.answerRepository.findById(id);
		
		if (answer.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(this.answerMapper.toAnswer(answer.get()));
	}catch (DataAccessException e) {
		LOGGER.error("Problème lors de la récupération d'une réponse",e);
		throw new AnswerException("Problème lors de la récupération d'une réponse");
	}
	}

}
