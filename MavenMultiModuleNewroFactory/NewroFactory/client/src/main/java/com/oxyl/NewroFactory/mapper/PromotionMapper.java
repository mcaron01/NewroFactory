package com.oxyl.NewroFactory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.back.PromotionEntity;
import com.oxyl.NewroFactory.model.Promotion;

@Component
public class PromotionMapper implements RowMapper<Promotion> {

	private final static Logger LOGGER = LoggerFactory.getLogger(InternMapper.class);

	private final static String ID = "id";
	private final static String NAME = "name";

	public PromotionMapper() {
	}

	@Override
	public Promotion mapRow(ResultSet rs, int rowNum) throws SQLException {

		try {
			Promotion promotion = new Promotion.PromotionBuilder(rs.getInt(ID), rs.getString(NAME)).build();
			LOGGER.info("La promotion a bien été créée dans le mapper");

			return promotion;
		} catch (SQLException e) {
			LOGGER.error("L'objet promotion n'a pas été créé dans le mapper", e);
			throw e;
		}
	}
	
	public Promotion mapPromotionDtoToPromotion(PromotionEntity promotionDto) {
		return new Promotion.PromotionBuilder(promotionDto.getId(), promotionDto.getName()).build();
	}
	
	public List<Promotion> mapListPromotionDtoToListPromotion(List<PromotionEntity> listPromotionDto) {
		return listPromotionDto.stream().map(n -> mapPromotionDtoToPromotion(n)).collect(Collectors.toList());
	}
}
