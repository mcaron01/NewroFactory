package com.oxyl.NewroFactory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.oxyl.NewroFactory.dto.back.InternEntity;
import com.oxyl.NewroFactory.dto.back.PromotionEntity;
import com.oxyl.NewroFactory.model.Intern;
import com.oxyl.NewroFactory.model.Intern.InternBuilder;
import com.oxyl.NewroFactory.model.Promotion;

@Component
public class InternMapper implements RowMapper<Intern> {

	private final static Logger LOGGER = LoggerFactory.getLogger(InternMapper.class);

	private final static String DATE_ARRIVEE = "I.arrival";
	private final static String DATE_FIN_FORMATION = "I.formation_over";
	private final static String ID_PROMOTION = "I.promotion_id";
	private final static String FIRST_NAME = "I.first_name";
	private final static String LAST_NAME = "I.last_name";
	private final static String ID = "I.id";
	private final static String NAME_PROMOTION = "P.name";

	public InternMapper() {
	}

	@Override
	public Intern mapRow(ResultSet rs, int rowNum) throws SQLException {
		LocalDate dateArrive = null;
		LocalDate dateFinFormation = null;

		try {
			if (rs.getDate(DATE_ARRIVEE) != null) {
				dateArrive = rs.getDate(DATE_ARRIVEE).toLocalDate();
			}
			if (rs.getDate(DATE_FIN_FORMATION) != null) {
				dateFinFormation = rs.getDate(DATE_FIN_FORMATION).toLocalDate();
			}

			Intern intern = new Intern.InternBuilder(rs.getString(FIRST_NAME), rs.getString(LAST_NAME), dateArrive,
					new Promotion.PromotionBuilder(rs.getInt(ID_PROMOTION), rs.getString(NAME_PROMOTION)).build())
					.id(rs.getInt(ID)).dateFinFormation(dateFinFormation).build();
			LOGGER.info("Le stagiaire a bien été créé dans le mapper");

			return intern;
		} catch (SQLException e) {
			LOGGER.error("Le stagiaire n'a pas été créé dans le mapper", e);
			throw e;
		}
	}

	public Optional<Intern> mapToIntern(ResultSet result) throws SQLException {
		LocalDate dateArrive = null;
		LocalDate dateFinFormation = null;
		try {
			if (result.isBeforeFirst()) {
				result.next();
			}

			if (result.getDate(DATE_ARRIVEE) != null) {
				dateArrive = result.getDate(DATE_ARRIVEE).toLocalDate();
			}
			if (result.getDate(DATE_FIN_FORMATION) != null) {
				dateFinFormation = result.getDate(DATE_FIN_FORMATION).toLocalDate();
			}
			Promotion promotion = new Promotion.PromotionBuilder(result.getInt(ID_PROMOTION),
					result.getString(NAME_PROMOTION)).build();
			Optional<Intern> intern = Optional
					.ofNullable(new Intern.InternBuilder(result.getString(FIRST_NAME), result.getString(LAST_NAME),
							dateArrive, promotion).id(result.getInt(ID)).dateFinFormation(dateFinFormation).build());
			LOGGER.info("Le stagiaire a bien été créé dans le mapper");
			return intern;
		} catch (SQLException e) {
			LOGGER.error("Le stagiaire n'a pas été créé dans le mapper", e);
			throw e;
		}
	}

	public List<Intern> mapToListIntern(ResultSet result) throws SQLException {
		List<Intern> internList = new ArrayList<Intern>();

		try {
			while (result.next()) {
				if (mapToIntern(result).isPresent()) {
					internList.add(mapToIntern(result).get());
				} else {
					LOGGER.warn("Un stagiaire n'a pas été intégré dans la liste des stagiaires");
				}
			}
			LOGGER.info("La liste de stagiaires a bien été créée dans le mapper");
		} catch (SQLException e) {
			LOGGER.error("La liste de stagiaires n'a pas été créée dans le mapper", e);
			throw e;
		}
		return internList;
	}

	public Intern mapInternDTOToIntern(InternEntity internDto) {

		Promotion promotion = new Promotion.PromotionBuilder(internDto.getPromotion().getId(),
				internDto.getPromotion().getName()).build();

		InternBuilder internBuilder = new Intern.InternBuilder(internDto.getFirstName(), internDto.getLastName(),
				internDto.getDateArrivee(), promotion);

		if (internDto.getId() != 0) {
			internBuilder.id(internDto.getId());
		}
		if (internDto.getDateFinFormation() != null) {
			internBuilder.dateFinFormation(internDto.getDateFinFormation());
		}
		LOGGER.info("Le mapping d'un InternDTO à un Intern a fonctionné");
		return internBuilder.build();
	}
	
	public InternEntity mapInternToInternDto(Intern intern) {
	
		return new InternEntity(intern.getId(), intern.getFirstName(), intern.getLastName(), intern.getDateArrivee(),
				intern.getDateFinFormation(), new PromotionEntity(intern.getPromotion().getId(), intern.getPromotion().getName()));
	}

	public List<Intern> mapListInternDtoToListIntern(List<InternEntity> listInternDto) {
		return listInternDto.stream().map(n -> mapInternDTOToIntern(n)).collect(Collectors.toList());
	}

}