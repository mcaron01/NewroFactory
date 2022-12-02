package com.oxyl.NewroFactory.mapper;

import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.front.AnswerDto;
import com.oxyl.NewroFactory.dto.front.ChapterDto;
import com.oxyl.NewroFactory.dto.front.QuestionDto;
import com.oxyl.NewroFactory.exception.QuestionException;
import com.oxyl.NewroFactory.model.Answer;
import com.oxyl.NewroFactory.model.Chapter;
import com.oxyl.NewroFactory.model.Question;
import com.oxyl.NewroFactory.service.AnswerService;
import com.oxyl.NewroFactory.service.QuestionService;

@Component
public class AnswerDtoMapper {
	private QuestionService questionService;
	public AnswerDtoMapper(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	public Answer toAnswer(AnswerDto answerDto) throws QuestionException {
		return new Answer.AnswerBuilder(answerDto.getLabel(), answerDto.getText(), answerDto.getValidAnswer(),
				this.questionService.getById(answerDto.getQuestionId()).get()).build();
	}
	
	public AnswerDto toDto(Answer answer) {
		return new AnswerDto(answer.getLabel(), answer.getText(), answer.getValidAnswer(),answer.getQuestion().getId());
	}
}
