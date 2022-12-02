package com.oxyl.NewroFactory.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oxyl.NewroFactory.dto.front.ChapterDto;
import com.oxyl.NewroFactory.dto.front.QuestionDto;
import com.oxyl.NewroFactory.exception.QuestionException;
import com.oxyl.NewroFactory.exception.AnswerException;
import com.oxyl.NewroFactory.exception.ChapterException;
import com.oxyl.NewroFactory.mapper.ChapterMapper;
import com.oxyl.NewroFactory.mapper.QuestionDtoMapper;
import com.oxyl.NewroFactory.model.Question;
import com.oxyl.NewroFactory.model.Chapter;
import com.oxyl.NewroFactory.service.QuestionService;
import com.oxyl.NewroFactory.service.ChapterService;

@RestController
@CrossOrigin
public class QuestionController {
	private final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

	private final String questionPath = "/questions/";
	private final String PAGE = "page";
	private final String NB_INTERNS = "nbQuestions";
	private final String SEARCH = "search";
	private final String ORDER = "orderBy";
	private final String ORDER_BY_DIRECTION = "orderByDirection";
	private final String ORDER_BY_DIRECTION_DESC = "desc";
	private final String title = "title";
	private final String NEXT = "next";
	private final String PREVIOUS = "previous";
	private QuestionService questionService;
	private QuestionDtoMapper questionDtoMapper;
	private ChapterController chapterController;
	private ChapterMapper chapterMapper;

	@Autowired
	public QuestionController(ChapterService chapterService, QuestionService questionService,
			QuestionDtoMapper questionDtoMapper, ChapterController chapterController, ChapterMapper chapterMapper) {
		this.questionService = questionService;
		this.questionDtoMapper = questionDtoMapper;
		this.chapterController = chapterController;
		this.chapterMapper = chapterMapper;
	}

	@PostMapping(questionPath)
	public Map<String, String> createQuestion(@Valid @RequestBody QuestionDto questionDto, BindingResult result)
			throws QuestionException, ChapterException {
		Map<String, String> errors = new HashMap<>();
		if (result.hasErrors()) {
			result.getFieldErrors().forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));
			if (result.getGlobalError() != null) {
				errors.put("dateFinFormation", result.getGlobalError().getDefaultMessage());
			}
		} else {
			Question question = this.questionDtoMapper.mapAddQuestionDtoToQuestion(questionDto);
			this.questionService.create(question);
		}
		return errors;
	}

	@GetMapping(questionPath + "{id}")
	public List<QuestionDto> getQuestionsByParent(@PathVariable int id, @RequestParam Map<String, String> params)
			throws ChapterException, QuestionException {
		List<Question> questions = new ArrayList<>();
		List<ChapterDto> chapters = this.chapterController.listAllChildChapters(id);
		List<Integer> ids = new ArrayList<>();
		chapters.stream().forEach(c -> ids.add(c.getId()));
		ids.add(id);
		Pageable page;
		if (this.isOrderByDesc(params)) {
			page = PageRequest.of(this.page(params) - 1, this.nbQuestions(params),
					Sort.by(this.order(params)).descending());
		} else {
			page = PageRequest.of(this.page(params) - 1, this.nbQuestions(params), Sort.by(this.order(params)));
		}
		System.out.println(ids);
		questions = this.questionService.getQuestionParentChapter(ids, page);
		System.out.println(questions.size());
		
		return this.questionDtoMapper.toListDto(questions);
	}
	
	@GetMapping(questionPath +"countByParentChapter/{id}")
	public ResponseEntity<Long> count(@PathVariable int id) throws ChapterException, QuestionException{
		List<ChapterDto> chapters = this.chapterController.listAllChildChapters(id);
		List<Integer> ids = new ArrayList<>();
		chapters.stream().forEach(c -> ids.add(c.getId()));
		ids.add(id);
		long count = this.questionService.countByChapterParent(ids);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	@PutMapping(questionPath + "{id}")
	public Map<String, String> editQuestions(@Valid @RequestBody QuestionDto questionDto, BindingResult result,
			@PathVariable int id) throws ChapterException, QuestionException {
		Map<String, String> errors = new HashMap<>();
		if (result.hasErrors()) {
			result.getFieldErrors().forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));
			if (result.getGlobalError() != null) {
				errors.put("dateFinFormation", result.getGlobalError().getDefaultMessage());
			}
		} else {
			Question question = this.questionDtoMapper.mapAddQuestionDtoToQuestion(questionDto);
			this.questionService.edit(id, question);
		}
		return errors;
	}

	@GetMapping(questionPath)
	public List<QuestionDto> questions(@RequestParam Map<String, String> params) throws QuestionException {
		List<Question> questions = new ArrayList<>();
		Pageable page;

		if (this.isOrderByDesc(params)) {
			page = PageRequest.of(this.page(params) - 1, this.nbQuestions(params),
					Sort.by(this.order(params)).descending());
		} else {
			page = PageRequest.of(this.page(params) - 1, this.nbQuestions(params), Sort.by(this.order(params)));
		}

		questions = this.questionService.getListQuestion(page);

		return this.questionDtoMapper.toListDto(questions);
	}

	@GetMapping(questionPath + "byChapter/{id}")
	public List<QuestionDto> questionsByChapter(@PathVariable int id, @RequestParam Map<String, String> params)
			throws QuestionException {
		List<Question> questions = new ArrayList<>();
		Pageable page;

		if (this.isOrderByDesc(params)) {
			page = PageRequest.of(this.page(params) - 1, this.nbQuestions(params),
					Sort.by(this.order(params)).descending());
		} else {
			page = PageRequest.of(this.page(params) - 1, this.nbQuestions(params), Sort.by(this.order(params)));
		}

		questions = this.questionService.getQuestionsByChapter(id, page);

		return this.questionDtoMapper.toListDto(questions);
	}

	@GetMapping(questionPath + "countByChapter/{id}")
	public ResponseEntity<Long> countQuestionsByChapter(@PathVariable int id) throws QuestionException {
		long count = this.questionService.getNbQuestionByChapter(id);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	@DeleteMapping(questionPath + "{id}")
	public ResponseEntity<Integer> delete(@PathVariable int id) throws QuestionException, AnswerException {
		Optional<Question> optionalQuestion = this.questionService.getById(id);
		if (!optionalQuestion.isEmpty()) {
			this.questionService.delete(optionalQuestion.get().getId());
		}
		return new ResponseEntity<Integer>(id, HttpStatus.OK);
	}

	@GetMapping(questionPath + "count")
	public ResponseEntity<Long> countQuestions(
			@RequestParam(name = "search", required = false, defaultValue = "") String search)
			throws QuestionException {
		long count = questionService.getNbQuestion(search);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	public int nbQuestions(Map<String, String> params) {
		int nbQuestions = 10;
		if (params.containsKey(NB_INTERNS)) {
			nbQuestions = Integer.parseInt(params.get(NB_INTERNS));
		}
		return nbQuestions;
	}

	public int page(Map<String, String> params) {
		int page = 1;
		if (params.containsKey(PAGE)) {
			page = Integer.parseInt(params.get(PAGE));
		}
		if (params.containsKey(NEXT)) {
			page = next(page);
		} else if (params.containsKey(PREVIOUS)) {
			page = previous(page);
		}
		return page;
	}

	public String order(Map<String, String> params) {
		String order = title;
		if (params.containsKey(ORDER)) {
			order = params.get(ORDER);
		}
		return order;
	}

	public String search(Map<String, String> params) {
		String search = "";
		if (params.containsKey(SEARCH)) {
			search = params.get(SEARCH);
		}
		return search;
	}

	public boolean isOrderByDesc(Map<String, String> params) {
		boolean orderByDesc = false;
		if (params.containsKey(ORDER_BY_DIRECTION) && params.get(ORDER_BY_DIRECTION).equals(ORDER_BY_DIRECTION_DESC)) {
			orderByDesc = true;
		}
		return orderByDesc;
	}

	public int next(int page) {
		return page + 5;
	}

	public int previous(int page) {
		if (page < 6) {
			page = 1;
		} else {
			page -= 5;
		}
		return page;
	}

}
