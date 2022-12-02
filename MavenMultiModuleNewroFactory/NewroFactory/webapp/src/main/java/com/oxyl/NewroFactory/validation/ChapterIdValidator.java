package com.oxyl.NewroFactory.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.oxyl.NewroFactory.exception.ChapterException;
import com.oxyl.NewroFactory.model.Chapter;
import com.oxyl.NewroFactory.service.ChapterService;

public class ChapterIdValidator implements ConstraintValidator<isInListChapter, String> {

	private final static Logger LOGGER = LoggerFactory.getLogger(ChapterIdValidator.class);

	private ChapterService chapterService;
	@Autowired
	public ChapterIdValidator(ChapterService chapterService) {
		this.chapterService = chapterService;
	}

	@Override
	public boolean isValid(String chapterName, ConstraintValidatorContext cvc) {
		List<Chapter> chapters = new ArrayList<Chapter>();
		try {
			chapters = this.chapterService.getAll();
		} catch (ChapterException e) {
		}
		for(Chapter p : chapters) {
			if(p.getName() == chapterName) {
				return false;
			}
		}
		LOGGER.error("La validation pour le chapitre est passée");
		return true;
	}

}
