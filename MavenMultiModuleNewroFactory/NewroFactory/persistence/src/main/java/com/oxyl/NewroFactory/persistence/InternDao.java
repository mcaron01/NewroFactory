package com.oxyl.NewroFactory.persistence;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oxyl.NewroFactory.dto.back.InternEntity;
import com.oxyl.NewroFactory.exception.InternException;
import com.oxyl.NewroFactory.mapper.InternMapper;
import com.oxyl.NewroFactory.model.Intern;
import com.oxyl.NewroFactory.persistence.jpa.InternRepository;

@Repository
public class InternDao {

	private final static Logger LOGGER = LoggerFactory.getLogger(InternDao.class);

	private InternMapper internMapper;
	private InternRepository internRepository;

	@Autowired
	public InternDao(InternMapper internMapper, InternRepository internRepository) {
		this.internMapper = internMapper;
		this.internRepository = internRepository;
	}

	public List<Intern> getListOfIntern(Pageable internPage) throws InternException {

		try {
			List<InternEntity> listInternEntity = internRepository.findAll(internPage).toList();
			List<Intern> listIntern = internMapper.mapListInternDtoToListIntern(listInternEntity);
			LOGGER.info("La requête pour obtenir une page des stagiaire fonctionne");
			return listIntern;
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour lister les stagiaires dans une page ne fonctionne pas", e);
			throw new InternException(
					"Désolé, il est impossible d'afficher la page de stagiaires demandée, veuillez selectionner le première page");
		}
	}


	public void create(Intern intern) throws InternException {

		try {
			internRepository.save(internMapper.mapInternToInternDto(intern));
			LOGGER.info("La requête pour créer un stagiaire fonctionne");
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour créer un stagiaire ne fonctionne pas", e);
			throw new InternException("Désole, nous n'avons pas réussi à créer le stagiaire");
		}
	}

	public void edit(Intern intern) throws InternException {

		try {
			internRepository.save(internMapper.mapInternToInternDto(intern));
			LOGGER.info("La requête pour modifier un stagiaire fonctionne");
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour modifier un stagiaire ne fonctionne pas", e);
			throw new InternException("Désolé, nous n'avons pas pu modifer ce stagiaire");
		}
	}
	
	public Optional<Intern> getById(int id) throws InternException {
	try {
		Optional<InternEntity> internEntity = this.internRepository.findById(id);
		if (internEntity.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(this.internMapper.mapInternDTOToIntern(internEntity.get()));
	} catch (DataAccessException e) {
		throw new InternException("Problème côté serveur pour l'obtention d'un stagiaire");
	}
}


	public void delete(int id) throws InternException {

		try {
			internRepository.deleteById(id);
			LOGGER.info("La requête pour supprimer un stagiaire fonctionne");
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour supprimer un stagiaire ne fonctionne pas", e);
			throw new InternException("Désolé, nous n'avons pas réussi a supprimer ce stagiaire");
		}
	}

	public long count(String searchPromotion) throws InternException {
		try {
			if (searchPromotion == null || searchPromotion.isEmpty()) {
				return internRepository.count();
			}
			else {
				return internRepository.countAllInternByPromotionName(searchPromotion);
			}

		} catch (DataAccessException e) {
			LOGGER.error("La requête pour compter le nombre d'éléments dans la table intern ne fonctionne pas", e);
			throw new InternException("Désolé, nous n'avons pas réussi a compter le nombre de stagiaires");
		}
	}

	public long countName(String searchInput, String searchPromotion) throws InternException {

		try {
			if (searchPromotion == null || searchPromotion.isEmpty()) {
				return internRepository.countAllInternByName(searchInput.concat("%"));
			}
			else {
				return internRepository.countAllInternByNameAndPromotion(searchInput.concat("%"), searchPromotion);
			}

		} catch (DataAccessException e) {
			LOGGER.error("La requête pour compter le nombre d'éléments dans la table intern ne fonctionne pas", e);
			throw new InternException("Désolé, nous n'avons pas réussi a compter le nombre de stagiaires");
		}
	}

	public long countCompleteName(String lastName, String firstName, String searchPromotion) throws InternException {

		try {
			if (searchPromotion == null || searchPromotion.isEmpty()) {
				return internRepository.countAllInternByCompleteName(firstName.concat("%"), lastName.concat("%"));
			}
			else {
				return internRepository.countAllInternByCompleteNameAndPromotion(firstName.concat("%"), lastName.concat("%"), searchPromotion);
			}

		} catch (DataAccessException e) {
			LOGGER.error("La requête pour compter le nombre d'éléments dans la table intern ne fonctionne pas", e);
			throw new InternException("Désolé, nous n'avons pas réussi a compter le nombre de stagiaires");
		}
	}

	public List<Intern> getListOfInternByCompleteName(Pageable internPage, String lastName, String firstName, String searchPromotion) throws InternException {

		try {
			List<InternEntity> listInternEntity;

			if (searchPromotion == null || searchPromotion.isEmpty()) {
				listInternEntity = internRepository.findAllInternsSearchByCompleteName(firstName.concat("%"),lastName.concat("%"), internPage);
			}
			else {
				listInternEntity = internRepository.findAllInternsSearchByCompleteNameAndPromotion(firstName.concat("%"),
						lastName.concat("%"), searchPromotion, internPage);
			}
			
			List<Intern> listIntern = internMapper.mapListInternDtoToListIntern(listInternEntity);
			LOGGER.info("La requête pour obtenir une liste de stagiaire avec nom - prénom fonctionne");
			return listIntern;
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour obtenir une liste de stagiaire avec le même nom - prénom ne fonctionne pas",
					e);
			throw new InternException("Désolé, il est impossible d'obtenir la liste de stagiaire avec ce nom - prénom");
		}
	}

	public List<Intern> getListOfInternByFirstNameOrLastName(Pageable internPage, String name, String searchPromotion) throws InternException {

		try {
			List<InternEntity> listInternEntity;

			if (searchPromotion == null || searchPromotion.isEmpty()) {
				listInternEntity = internRepository.findAllInternByName(name.concat("%"), internPage);
			}
			else {
				listInternEntity = internRepository.findAllInternByNameAndPromotion(name.concat("%"), searchPromotion, internPage);
			}
			
			List<Intern> listIntern = internMapper.mapListInternDtoToListIntern(listInternEntity);
			LOGGER.info("La requête pour obtenir une liste de stagiaire avec nom ou prénom fonctionne");
			return listIntern;
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour obtenir une liste de stagiaire avec le même nom ou prénom ne fonctionne pas",
					e);
			throw new InternException(
					"Désolé, il est impossible d'obtenir la liste de stagiaire avec ce nom ou prénom");
		}
	}
	
	public List<Intern> getListOfInternByPromotionName(Pageable internPage, String promotionName) throws InternException {

		try {
			List<InternEntity> listInternEntity = internRepository.findAllInternByPromotionName("%" + promotionName + "%", internPage);
			List<Intern> listIntern = internMapper.mapListInternDtoToListIntern(listInternEntity);
			LOGGER.info("La requête pour obtenir une liste de stagiaire avec le nom de la promotion fonctionne");
			return listIntern;
		} catch (DataAccessException e) {
			LOGGER.error(
					"La requête pour obtenir une liste de stagiaire  avec le nom de la promotion ne fonctionne pas", e);
			throw new InternException(
					"Désolé, il est impossible d'obtenir la liste de stagiaire avec le nom de la promotion");
		}
	}
	
	public List<Intern> getListOfInternOrderBy(Pageable internPage) throws InternException {

		try {
			List<InternEntity> listInternEntity = internRepository.findAll(internPage).toList();
			List<Intern> listIntern = internMapper.mapListInternDtoToListIntern(listInternEntity);	
			LOGGER.info("La requête pour obtenir une page des stagiaire ordonné fonctionne");
			return listIntern;
		} catch (DataAccessException e) {
			LOGGER.error("La requête pour lister les stagiaires dans une page ordonné ne fonctionne pas", e);
			throw new InternException("Désolé, il est impossible d'afficher la page ordonné de stagiaires demandée");
		}
	}
}