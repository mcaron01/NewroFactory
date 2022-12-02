package com.oxyl.NewroFactory.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.front.QuestionDto;
import com.oxyl.NewroFactory.dto.front.ChapterDto;
import com.oxyl.NewroFactory.exception.ChapterException;
import com.oxyl.NewroFactory.model.Question;
import com.oxyl.NewroFactory.model.Chapter;
import com.oxyl.NewroFactory.service.ChapterService;

@Component
public class QuestionDtoMapper {

	private final static Logger LOGGER = LoggerFactory.getLogger(QuestionDtoMapper.class);

	private ChapterService chapterService;

	@Autowired
	public QuestionDtoMapper(ChapterService chapterService) {
		this.chapterService = chapterService;
	}

	public Question mapAddQuestionDtoToQuestion(QuestionDto questionDto) throws ChapterException {
		Chapter chapter = chapterService.createInstanceChapterWithId(questionDto.getChapter().getId());
		Question question = new Question.QuestionBuilder(questionDto.getTitle(), questionDto.getStatement(), chapter).build();
		return question;
	}
	
	public QuestionDto toQuestionDto(Question question) {
		Chapter chapter = question.getChapter();
		return new QuestionDto(question.getId(),question.getTitle(), question.getStatement(), new ChapterDto(chapter.getId(), chapter.getName(),chapter.getParentPath(),new ArrayList<>()));
	}

	public List<QuestionDto> toListDto(List<Question> questions){
		return questions.stream().map(i->this.toQuestionDto(i)).toList();
	}

}
