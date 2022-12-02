package com.oxyl.NewroFactory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxyl.NewroFactory.dto.front.AnswerDto;
import com.oxyl.NewroFactory.exception.AnswerException;
import com.oxyl.NewroFactory.exception.InternException;
import com.oxyl.NewroFactory.exception.QuestionException;
import com.oxyl.NewroFactory.mapper.AnswerDtoMapper;
import com.oxyl.NewroFactory.model.Answer;
import com.oxyl.NewroFactory.service.AnswerService;

@RestController
@CrossOrigin
public class AnswerController {
	private AnswerService answerService;
	private AnswerDtoMapper answerDtoMapper;
	
	public AnswerController(AnswerService answerService, AnswerDtoMapper answerDtoMapper) {
		this.answerService = answerService;
		this.answerDtoMapper = answerDtoMapper;
	}
	
	@GetMapping("/answer/{id}")
	public List<AnswerDto> answers(@PathVariable int id) throws AnswerException{
		List<Answer> answers= this.answerService.getAnswers(id);
		return answers.stream().map(a->this.answerDtoMapper.toDto(a)).toList();
	}
	
	@PostMapping("/answer")
	public Map<String,String> create(@Valid @RequestBody AnswerDto answerDto, BindingResult result) throws AnswerException, QuestionException{
		Map<String,String> errors = new HashMap<>();
		Answer answer = this.answerDtoMapper.toAnswer(answerDto);
		if(result.hasErrors()) {
			
		}
		this.answerService.create(answer);
		return errors;
	}
	
	@PutMapping("/answer/{id}")
	public Map<String,String> modify(@Valid @RequestBody AnswerDto answerDto,@PathVariable int id, BindingResult result) throws AnswerException, QuestionException{
		Map<String,String> errors = new HashMap<>();
		Answer answer = this.answerDtoMapper.toAnswer(answerDto);
		answer.setId(id);
		if(result.hasErrors()) {
			
		}
		this.answerService.modify(answer);
		return errors;
	}
	
	@GetMapping("/answer/detail/{id}")
	public AnswerDto getAnswer(@PathVariable int id) throws AnswerException {
		Optional<Answer> optionalAnswer = this.answerService.getAnswer(id);
		if (optionalAnswer.isEmpty()) {
			throw new AnswerException("Answer not found");
		}
		return this.answerDtoMapper.toDto(optionalAnswer.get());
	}
}
