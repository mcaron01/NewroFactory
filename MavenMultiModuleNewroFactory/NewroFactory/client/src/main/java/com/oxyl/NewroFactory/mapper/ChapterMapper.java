package com.oxyl.NewroFactory.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.back.ChapterEntity;
import com.oxyl.NewroFactory.model.Chapter;

@Component
public class ChapterMapper {

	private final static Logger LOGGER = LoggerFactory.getLogger(ChapterMapper.class);

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String PARENT_PATH = "parent_path";

	public ChapterMapper() {
	}

	public Chapter mapChapterDtoToChapter(ChapterEntity chapterDto) {
		return new Chapter.ChapterBuilder(chapterDto.getName(), chapterDto.getParentPath()).id(chapterDto.getId()).build();
	}
	
	public List<Chapter> mapListChapterDtoToListChapter(List<ChapterEntity> listChapterDto) {
		return listChapterDto.stream().map(n -> mapChapterDtoToChapter(n)).collect(Collectors.toList());
	}
	
	public ChapterEntity toEntity(Chapter chapter) {
		return new ChapterEntity(chapter.getId(), chapter.getName(), chapter.getParentPath());
	}
}
