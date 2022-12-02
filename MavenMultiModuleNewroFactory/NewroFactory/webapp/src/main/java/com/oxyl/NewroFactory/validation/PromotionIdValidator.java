package com.oxyl.NewroFactory.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.oxyl.NewroFactory.exception.PromotionException;
import com.oxyl.NewroFactory.model.Promotion;
import com.oxyl.NewroFactory.service.PromotionService;

public class PromotionIdValidator implements ConstraintValidator<isInListPromotion, Integer> {

	private final static Logger LOGGER = LoggerFactory.getLogger(PromotionIdValidator.class);

	private PromotionService promotionService;
	@Autowired
	public PromotionIdValidator(PromotionService promotionService) {
		this.promotionService = promotionService;
	}

	@Override
	public boolean isValid(Integer promotionId, ConstraintValidatorContext cvc) {
		List<Promotion> promotions = new ArrayList<Promotion>();
		try {
			promotions = this.promotionService.getAll();
		} catch (PromotionException e) {
		}
		for(Promotion p : promotions) {
			if(p.getId() == promotionId) {
				return true;
			}
		}
		LOGGER.error("La validation pour la promotion est passée");
		return false;
	}

}
