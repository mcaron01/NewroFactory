package com.oxyl.NewroFactory.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oxyl.NewroFactory.dto.front.ChapterDto;
import com.oxyl.NewroFactory.exception.ChapterException;
import com.oxyl.NewroFactory.mapper.ChapterDtoMapper;
import com.oxyl.NewroFactory.model.Chapter;
import com.oxyl.NewroFactory.service.ChapterService;

@RestController
@CrossOrigin
public class ChapterController {
	private ChapterService chapterService;
	private ChapterDtoMapper chapterDtoMapper;
	
	public ChapterController(ChapterService chapterService, ChapterDtoMapper chapterDtoMapper) {
		this.chapterService = chapterService;
		this.chapterDtoMapper = chapterDtoMapper;
	}
	
	@GetMapping("/chapter")
	public List<ChapterDto> listChapters() throws ChapterException {
		List<Chapter> chapters;
			chapters = this.chapterService.listParentChapter();
		List<ChapterDto> chapterDto =  chapters.stream().map(c->this.chapterDtoMapper.toChapterDto(c)).toList();
		return chapterDto;
	}

	@GetMapping("/chapterDetails/{id}")
	public ChapterDto getChapterById(@PathVariable int id) throws ChapterException {
		Optional<Chapter> optionalChapter = this.chapterService.getById(id);
		if (optionalChapter.isEmpty()) {
			throw new ChapterException("Chapter not found");
		}
		ChapterDto chapterDto = this.chapterDtoMapper.toChapterDto(optionalChapter.get());
		return chapterDto;
	}
	
	@GetMapping("/chapter/{id}")
	public List<ChapterDto> listChildChapters(@PathVariable int id) throws ChapterException{
		Optional<Chapter> optionalChapter = this.chapterService.getById(id);
		if (optionalChapter.isEmpty()) {
			throw new ChapterException("Chapter not found");
		}
		Chapter chapter = optionalChapter.get();
		List<Chapter> chapters = this.chapterService.listChildChapter(chapter.getName(),chapter.getParentPath() );
		List<ChapterDto> chapterDto =  chapters.stream().map(c->this.chapterDtoMapper.toChapterDto(c)).toList();
		List<ChapterDto> aux = chapterDto;
		while(!aux.isEmpty()) {
			List<ChapterDto> aux2 = new ArrayList<>();
		aux.stream().forEach(c->{
			List<Chapter> childChapters;
			try {
				childChapters = this.chapterService.listChildChapter(c.getName(),c.getParentPath());
				c.setChildren(childChapters.stream().map(child->this.chapterDtoMapper.toChapterDto(child)).toList());
				c.getChildren().stream().forEach(child->aux2.add(child));
			} catch (ChapterException e) {
			}
		});
		aux = aux2;
		}
		return chapterDto;
	}
	

	@GetMapping("/chapter/all/{id}")
	public List<ChapterDto> listAllChildChapters(@PathVariable int id) throws ChapterException{
		Optional<Chapter> optionalChapter = this.chapterService.getById(id);
		if (optionalChapter.isEmpty()) {
			throw new ChapterException("Chapter not found");
		}
		Chapter chapter = optionalChapter.get();
		List<Chapter> chapters = this.chapterService.listChildChapter(chapter.getName(),chapter.getParentPath() );
		List<ChapterDto> chapterDto =  chapters.stream().map(c->this.chapterDtoMapper.toChapterDto(c)).toList();
		List<ChapterDto> aux = chapterDto;
		List<ChapterDto> aux3 = new ArrayList<>(chapterDto);
		while(!aux.isEmpty()) {
			List<ChapterDto> aux2 = new ArrayList<>();
		aux.stream().forEach(c->{
			List<Chapter> childChapters;
			try {
				childChapters = this.chapterService.listChildChapter(c.getName(),c.getParentPath());
				c.setChildren(childChapters.stream().map(child->this.chapterDtoMapper.toChapterDto(child)).toList());
				c.getChildren().stream().forEach(child->aux2.add(child));
				c.getChildren().stream().forEach(child->aux3.add(child));
			} catch (ChapterException e) {
			}
		});
		aux = aux2;
		}
		System.out.println(aux3.size());
		return aux3;
	}
	
	
	@PostMapping("/chapter")
	public Map<String,String> create(@Valid @RequestBody ChapterDto chapterDto, BindingResult result) throws ChapterException{
		Map<String,String> errors = new HashMap<String,String>();
		if(result.hasErrors()) {
			result
            .getFieldErrors()
            .forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));
		}
		else {
			Chapter chapter = this.chapterDtoMapper.toChapter(chapterDto);
			this.chapterService.create(chapter);
		}
		return errors;
		
	}
	
	@DeleteMapping("/chapter/{id}")
	public ResponseEntity<Integer> delete(@PathVariable int id) throws ChapterException{
		this.chapterService.delete(id);
		return new ResponseEntity<Integer>(id, HttpStatus.OK);
	}
	
}
