package com.oxyl.NewroFactory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oxyl.NewroFactory.exception.PromotionException;
import com.oxyl.NewroFactory.model.Promotion;
import com.oxyl.NewroFactory.service.PromotionService;

@RestController
@CrossOrigin
public class PromotionController {
	private PromotionService promotionService;
	public PromotionController(PromotionService promotionService) {
		this.promotionService = promotionService;
	}
	@GetMapping("/promotions")
	public List<Promotion> promotions() throws PromotionException {
		List<Promotion> promotions = new ArrayList<>();
		promotions = this.promotionService.getAll();
		return promotions;
	}
}
