package com.oxyl.NewroFactory.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oxyl.NewroFactory.exception.PromotionException;
import com.oxyl.NewroFactory.model.Promotion;
import com.oxyl.NewroFactory.persistence.PromotionPersistence;

@Service
@Transactional
public class PromotionService {

	private final static Logger LOGGER = LoggerFactory.getLogger(PromotionService.class);

	private PromotionPersistence promotionPersistence;

	@Autowired
	public PromotionService(PromotionPersistence promotionPersistence) {
		this.promotionPersistence = promotionPersistence;
	}

	public void printList(List<Promotion> listPromotion) {
		for (Promotion p : listPromotion)
			System.out.println(p);
		LOGGER.info("La liste des promotions a bien été affiché");
	}

	public boolean isInListPromotion(int id) throws PromotionException {
		List<Promotion> listPromotion = promotionPersistence.getListOfAllPromotion();
		boolean isInListOfPromo = listPromotion.stream().filter(x -> Objects.equals(x.getId(), id)).count() > 0;
		LOGGER.info("Le test de savoir si la promotion existe est passé");
		return isInListOfPromo;
	}

	public Optional<Promotion> createInstancePromotionWithId(int id) throws PromotionException {
		Optional<Promotion> promotion = promotionPersistence.getPromotionWithId(id);
		LOGGER.info("L'instance de promotion avec id a bien été créée");
		return promotion;
	}

	public List<Promotion> getAll() throws PromotionException {
		List<Promotion> listPromotion = promotionPersistence.getListOfAllPromotion();
		LOGGER.info("La liste des promotions a bien été recupéré");
		return listPromotion;
	}

}
