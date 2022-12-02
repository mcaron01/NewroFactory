package com.oxyl.NewroFactory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.oxyl.NewroFactory.exception.AnswerException;
import com.oxyl.NewroFactory.model.Answer;
import com.oxyl.NewroFactory.model.Question;
import com.oxyl.NewroFactory.persistence.AnswerPersistence;

@Service
public class AnswerService {
	private AnswerPersistence answerPersistence;
	public AnswerService(AnswerPersistence answerPersistence) {
		this.answerPersistence = answerPersistence;
	}
	
	public List<Answer> getAnswers(int questionId) throws AnswerException{
		return this.answerPersistence.getAnswers(questionId);
	}
	
	public void delete(int id) throws AnswerException {
		this.answerPersistence.delete(id);
	}
	
	public void create(Answer answer) throws AnswerException {
		this.answerPersistence.create(answer);
	}
	
	public void modify(Answer answer) throws AnswerException {
		this.answerPersistence.modify(answer);
	}
	
	public Optional<Answer> getAnswer(int id) throws AnswerException{
		return this.answerPersistence.getById(id);
	}
}
