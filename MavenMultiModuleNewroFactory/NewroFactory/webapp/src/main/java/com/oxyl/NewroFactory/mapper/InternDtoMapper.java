package com.oxyl.NewroFactory.mapper;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.front.InternDto;
import com.oxyl.NewroFactory.dto.front.PromotionDto;
import com.oxyl.NewroFactory.exception.PromotionException;
import com.oxyl.NewroFactory.model.Intern;
import com.oxyl.NewroFactory.model.Promotion;
import com.oxyl.NewroFactory.service.PromotionService;

@Component
public class InternDtoMapper {

	private final static Logger LOGGER = LoggerFactory.getLogger(InternDtoMapper.class);

	private PromotionService promotionService;

	@Autowired
	public InternDtoMapper(PromotionService promotionService) {
		this.promotionService = promotionService;
	}


	public Intern mapAddInternDtoToIntern(InternDto internDto) throws PromotionException {
		Optional<Promotion> promotion = promotionService.createInstancePromotionWithId(internDto.getPromotion().getId());
		LOGGER.info("La promotion a bien été créé dans la mapper internDto");
		if(promotion.isEmpty()) {
			throw new PromotionException("Promotion non présente");
		}
		if (internDto.getDateFinFormation() != null) {
			Intern intern = new Intern.InternBuilder(internDto.getFirstName(), internDto.getLastName(), internDto.getDateArrivee(), promotion.get())
					.dateFinFormation(internDto.getDateFinFormation()).build();
			LOGGER.info("Le stagiaire a bien été créé dans la mapper internDto");
			return intern;
		} else {
			Intern intern = new Intern.InternBuilder(internDto.getFirstName(), internDto.getLastName(), internDto.getDateArrivee(), promotion.get()).build();
			LOGGER.info("Le stagiaire a bien été créé dans la mapper internDto");
			return intern;
		}
	}
	
	public InternDto toInternDto(Intern intern) {
		return new InternDto(intern.getId(),intern.getFirstName(), intern.getLastName(), intern.getDateArrivee(),
				intern.getDateFinFormation(),new PromotionDto(intern.getPromotion().getId(), intern.getPromotion().getName()));
	}

	public List<InternDto> toListDto(List<Intern> interns){
		return interns.stream().map(i->this.toInternDto(i)).toList();
	}

}
