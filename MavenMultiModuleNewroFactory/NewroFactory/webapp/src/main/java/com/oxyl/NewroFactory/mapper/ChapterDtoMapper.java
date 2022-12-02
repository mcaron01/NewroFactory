package com.oxyl.NewroFactory.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.front.ChapterDto;
import com.oxyl.NewroFactory.model.Chapter;

@Component
public class ChapterDtoMapper {

	public ChapterDtoMapper() {
	}
	
	public ChapterDto toChapterDto(Chapter chapter) {
		return new ChapterDto(chapter.getId(), chapter.getName(), chapter.getParentPath(),new ArrayList<>());
	}
	
	public Chapter toChapter(ChapterDto chapterDto) {
		return new Chapter.ChapterBuilder(chapterDto.getName(),chapterDto.getParentPath()).id(chapterDto.getId()).build();
	}
}
