package com.oxyl.NewroFactory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.back.QuestionEntity;
import com.oxyl.NewroFactory.dto.back.ChapterEntity;
import com.oxyl.NewroFactory.model.Chapter;
import com.oxyl.NewroFactory.model.Question;
import com.oxyl.NewroFactory.model.Question.QuestionBuilder;

@Component
public class QuestionMapper {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(QuestionMapper.class);
	
	private final static String ID = "Q.id";
	private final static String CHAPTER_ID = "Q.chapter_id";
	private final static String TITLE = "Q.title";
	private final static String STATEMENT = "Q.statement";
	private final static String CHAPTER_NAME = "C.name";
	private final static String CHAPTER_PARENT_PATH = "C.parent_path";

	public QuestionMapper() {
	}

	public Optional<Question> mapToQuestion(ResultSet result) throws SQLException {
		try {
			if (result.isBeforeFirst()) {
				result.next();
			}

			Chapter chapter = new Chapter.ChapterBuilder(result.getString(CHAPTER_NAME),
					result.getString(CHAPTER_PARENT_PATH)).id(result.getInt(CHAPTER_ID)).build();
			Optional<Question> question = Optional
					.ofNullable(new Question.QuestionBuilder(result.getString(TITLE), result.getString(STATEMENT), chapter)
					.id(result.getInt(ID)).build());
			LOGGER.info("La question a bien été créée dans le mapper");
			return question;
		} catch (SQLException e) {
			LOGGER.error("La question n'a pas été créée dans le mapper", e);
			throw e;
		}
	}

	public List<Question> mapToListQuestion(ResultSet result) throws SQLException {
		List<Question> questionList = new ArrayList<Question>();

		try {
			while (result.next()) {
				if (mapToQuestion(result).isPresent()) {
					questionList.add(mapToQuestion(result).get());
				} else {
					LOGGER.warn("Une question n'a pas été intégrée dans la liste des questions");
				}
			}
			LOGGER.info("La liste de questions a bien été créée dans le mapper");
		} catch (SQLException e) {
			LOGGER.error("La liste de questions n'a pas été créée dans le mapper", e);
			throw e;
		}
		return questionList;
	}

	public Question mapQuestionDTOToQuestion(QuestionEntity questionDto) {
		ChapterEntity chapterEntity = questionDto.getChapter();

		Chapter chapter = new Chapter.ChapterBuilder(chapterEntity.getName(), chapterEntity.getParentPath()).id(chapterEntity.getId()).build();

		QuestionBuilder questionBuilder = new Question.QuestionBuilder(questionDto.getTitle(), questionDto.getStatement(), chapter);

		if (questionDto.getId() != 0) {
			questionBuilder.id(questionDto.getId());
		}

		LOGGER.info("Le mapping d'un QuestionDTO à un Question a fonctionné");
		return questionBuilder.build();
	}
	
	public QuestionEntity mapQuestionToQuestionDto(Question question) {
		Chapter chapter = question.getChapter();
	
		return new QuestionEntity(question.getId(), question.getTitle(), question.getStatement(), 
				new ChapterEntity(chapter.getId(), chapter.getName(), chapter.getParentPath()));
	}

	public List<Question> mapListQuestionDtoToListQuestion(List<QuestionEntity> listQuestionDto) {
		return listQuestionDto.stream().map(n -> mapQuestionDTOToQuestion(n)).collect(Collectors.toList());
	}
}
