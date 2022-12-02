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

import com.oxyl.NewroFactory.dto.front.InternDto;
import com.oxyl.NewroFactory.exception.InternException;
import com.oxyl.NewroFactory.exception.PromotionException;
import com.oxyl.NewroFactory.mapper.InternDtoMapper;
import com.oxyl.NewroFactory.model.Intern;
import com.oxyl.NewroFactory.model.Promotion;
import com.oxyl.NewroFactory.service.InternService;
import com.oxyl.NewroFactory.service.PromotionService;

@RestController
@CrossOrigin
public class InternController {
	private static Logger LOGGER = LoggerFactory.getLogger(InternController.class);

	private final String PAGE = "page";
	private final String NB_INTERNS = "nbInterns";
	private final String SEARCH = "search";
	private final String SEARCH_PROMOTION = "searchPromotion";
	private final String ORDER = "orderBy";
	private final String ORDER_BY_DIRECTION = "orderByDirection";
	private final String ORDER_BY_DIRECTION_DESC = "desc";
	private final String LAST_NAME = "lastName";
	private final String NEXT = "next";
	private final String PREVIOUS = "previous";

	private InternService internService;
	private InternDtoMapper internDtoMapper;

	@Autowired
	public InternController(InternService internService,
			InternDtoMapper internDtoMapper) {
		this.internService = internService;
		this.internDtoMapper = internDtoMapper;
	}


	@PostMapping("/addStagiaire")
	public Map<String,String> addStagiaire(@Valid @RequestBody InternDto internDto, BindingResult result)
			throws InternException, PromotionException {
		Map<String,String> errors = new HashMap<>();
		if (result.hasErrors()) {
			result
            .getFieldErrors()
            .forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));
			if(result.getGlobalError() != null) {
			errors.put("dateFinFormation", result.getGlobalError().getDefaultMessage());
			}
		} else {
			Intern intern = this.internDtoMapper.mapAddInternDtoToIntern(internDto);
			this.internService.create(intern);
		}
		return errors;
	}
	
	@GetMapping("/editStagiaire/{id}")
	public InternDto getEdit(@PathVariable int id) throws InternException, PromotionException {
		Optional<Intern> optionalIntern = this.internService.getById(id);
		if (optionalIntern.isEmpty()) {
			throw new InternException("Intern not found");
		}
		InternDto internDto = this.internDtoMapper.toInternDto(optionalIntern.get());
		return internDto;

	}
	
	@PutMapping("/editStagiaire/{id}")
	public Map<String,String> editStagiaire(@Valid @RequestBody InternDto internDto, BindingResult result,
			@PathVariable int id) throws PromotionException, InternException {
		Map<String,String> errors = new HashMap<>();
		if (result.hasErrors()) {
			result
            .getFieldErrors()
            .forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));
			if(result.getGlobalError() != null) {
			errors.put("dateFinFormation", result.getGlobalError().getDefaultMessage());
			}
		} else {
			Intern intern = this.internDtoMapper.mapAddInternDtoToIntern(internDto);
			this.internService.edit(id,intern);
		}
		return errors;
	}

	@GetMapping("/dashboard")
	public List<InternDto> interns(@RequestParam Map<String, String> params) throws InternException {
		List<Intern> interns = new ArrayList<>();
		Pageable page;
		
		if (this.isOrderByDesc(params)) {
			page = PageRequest.of(this.page(params) - 1, this.nbInterns(params), Sort.by(this.order(params)).descending());
		} else {
			page = PageRequest.of(this.page(params) - 1, this.nbInterns(params), Sort.by(this.order(params)));
		}
		
		String searchInput = this.search(params).trim();
		String searchPromotionInput = this.searchPromotion(params);

		if (searchInput == null || searchInput.isEmpty()) {
			if (searchPromotionInput == null || searchPromotionInput.isEmpty()) {
				interns = this.internService.getListIntern(page);
			}
			else {
				interns = this.internService.getListInternByPromotionName(page, searchPromotionInput);
			}
		} else {
			String[] searchInputs = searchInput.split(" ");
			if (searchInputs.length == 2) {
				interns = this.internService.getListInternByCompleteName(page, searchInputs[0], searchInputs[1], searchPromotionInput);
			} else if (searchInputs.length == 1) {
				interns = this.internService.getListInternByName(page, searchInputs[0], searchPromotionInput);
			}
		}
		
		return this.internDtoMapper.toListDto(interns);
	}

	@GetMapping("/count")
	public ResponseEntity<Long> countInterns(@RequestParam Map<String, String> params) throws InternException {
		String searchInput = this.search(params).trim();
		String searchPromotionInput = this.searchPromotion(params);
		long count = 0;

		if (searchInput == null || searchInput.isEmpty()) {
			count = this.internService.getNbIntern(searchPromotionInput);
		} else {
			String[] searchInputs = searchInput.split(" ");
			if (searchInputs.length == 2) {
				count = this.internService.getNbInternSearchCompleteName(searchInputs[0], searchInputs[1], searchPromotionInput);
			} else if (searchInputs.length == 1) {
				count = this.internService.getNbInternSearchName(searchInputs[0], searchPromotionInput);
			}
		}
		
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	public int nbInterns(Map<String, String> params) {
		int nbInterns = 10;
		if (params.containsKey(NB_INTERNS)) {
			nbInterns = Integer.parseInt(params.get(NB_INTERNS));
		}
		return nbInterns;
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
		String order = LAST_NAME;
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

	public String searchPromotion(Map<String, String> params) {
		String searchPromotion = "";
		if (params.containsKey(SEARCH_PROMOTION)) {
			searchPromotion = params.get(SEARCH_PROMOTION);
		}
		return searchPromotion;
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


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Integer> delete(@PathVariable int id) throws InternException {
		this.internService.delete(id);
		return new ResponseEntity<Integer>(id, HttpStatus.OK);
	}
	
}
