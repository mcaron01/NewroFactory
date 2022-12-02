package com.oxyl.NewroFactory.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.oxyl.NewroFactory.dto.back.PromotionEntity;
import com.oxyl.NewroFactory.exception.PromotionException;
import com.oxyl.NewroFactory.mapper.PromotionMapper;
import com.oxyl.NewroFactory.model.Promotion;
import com.oxyl.NewroFactory.persistence.jpa.PromotionRepository;

@Repository
public class PromotionPersistence {

	private final static Logger LOGGER = LoggerFactory.getLogger(PromotionPersistence.class);

	private PromotionMapper promotionMapper;
	private PromotionRepository promotionRepository;

	@Autowired
	public PromotionPersistence(PromotionMapper promotionMapper, PromotionRepository promotionRepository) {
		this.promotionMapper = promotionMapper;
		this.promotionRepository = promotionRepository;
	}

	public Optional<Promotion> getPromotionWithId(int id) throws PromotionException {

		try {
			Optional<PromotionEntity> promotion = this.promotionRepository.findById(id);
			if(promotion.isEmpty()) {
				return Optional.empty();
			}
			return Optional.ofNullable(this.promotionMapper.mapPromotionDtoToPromotion(promotion.get()));
		} catch (DataAccessException e) {
			LOGGER.error("Erreur dans la récupération du nom de la promotion", e);
			throw new PromotionException("Désolé, le nom de la promotion n'a pas été bien récupéré");
		}
	}

	public List<Promotion> getListOfAllPromotion() throws PromotionException {

		try {
			return this.promotionMapper.mapListPromotionDtoToListPromotion(this.promotionRepository.findAll());
		} catch (DataAccessException e) {
			LOGGER.error("Exception due a la requete", e);
			throw new PromotionException("Désolé, il est impossible d'afficher la liste des promotions");
		}
	}
}
