package com.oxyl.NewroFactory.mapper;

import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.back.AnswerEntity;
import com.oxyl.NewroFactory.model.Answer;

@Component
public class AnswerMapper {

	private QuestionMapper questionMapper;
	public AnswerMapper(QuestionMapper questionMapper) {
		this.questionMapper = questionMapper;
	}
	
	public Answer toAnswer (AnswerEntity answerEntity) {
		return new Answer.AnswerBuilder(answerEntity.getLabel(), answerEntity.getText(), answerEntity.getValidAnswer(),
				this.questionMapper.mapQuestionDTOToQuestion(answerEntity.getQuestion())).id(answerEntity.getId()).build();
	}
	
	public AnswerEntity toEntity(Answer answer) {
		return new AnswerEntity(answer.getId(),answer.getLabel(),answer.getText(),answer.getValidAnswer(),
				this.questionMapper.mapQuestionToQuestionDto(answer.getQuestion()));
	}
}
