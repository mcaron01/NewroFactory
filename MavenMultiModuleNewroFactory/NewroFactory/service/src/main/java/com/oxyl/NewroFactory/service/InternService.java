package com.oxyl.NewroFactory.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oxyl.NewroFactory.exception.InternException;
import com.oxyl.NewroFactory.model.Intern;
import com.oxyl.NewroFactory.persistence.InternDao;

@Service
public class InternService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(InternService.class);

	private InternDao internPersistence;

	@Autowired
	public InternService(InternDao internPersistence) {
		this.internPersistence = internPersistence;
	}

	public void create(Intern intern) throws InternException {
		internPersistence.create(intern);
		LOGGER.info("La stagiaire a bien été ajouté a la BDD");
	}

	public void edit(int id, Intern intern) throws InternException {
		intern.setId(id);
		internPersistence.edit(intern);
		LOGGER.info("La stagiaire a bien été modifié dans la BDD");
	}

	public void delete(int id) throws InternException {
		internPersistence.delete(id);
		LOGGER.info("La stagiaire a bien été supprimé de la BDD");

	}

	public Optional<Intern> getById(int id) throws InternException {
	try {
		return this.internPersistence.getById(id);
	} catch (InternException e) {
		LOGGER.error(e.getMessage(), e);
		throw e;
	}
}


	public List<Intern> getListIntern(Pageable internPage) throws InternException {
		List<Intern> listIntern = internPersistence.getListOfIntern(internPage);
		LOGGER.info("La liste de stagiaire a bien été récupérée");
		return listIntern;
	}

	public long getNbIntern(String searchPromotionInput) throws InternException {
		return internPersistence.count(searchPromotionInput);
	}

	public long getNbInternSearchName(String searchInput, String searchPromotionInput) throws InternException {
		return internPersistence.countName(searchInput, searchPromotionInput);
	}

	public long getNbInternSearchCompleteName(String firstNameInput, String lastNameInput, String searchPromotionInput) throws InternException {
		return internPersistence.countCompleteName(firstNameInput, lastNameInput, searchPromotionInput);
	}


	public List<Intern> getListInternByCompleteName(Pageable internPage, String lastName, String firstName, String promotion) throws InternException {
		List<Intern> listIntern = internPersistence.getListOfInternByCompleteName(internPage, lastName, firstName, promotion);
		LOGGER.info("La liste de stagiaire a bien été récupérée");
		return listIntern;
	}

	public List<Intern> getListInternByName(Pageable internPage, String name, String promotion) throws InternException {
		List<Intern> listIntern = internPersistence.getListOfInternByFirstNameOrLastName(internPage, name, promotion);
		LOGGER.info("La liste de stagiaire a bien été récupérée");
		return listIntern;
	}

	public List<Intern> getListInternByPromotionName(Pageable internPage, String promotionName) throws InternException {
		List<Intern> listIntern = internPersistence.getListOfInternByPromotionName(internPage, promotionName);
		LOGGER.info("La liste de stagiaire a bien été récupérée");
		return listIntern;
	}

	public List<Intern> getListInternOrderBy(Pageable internPage) throws InternException {
		List<Intern> listIntern = internPersistence.getListOfInternOrderBy(internPage);
		LOGGER.info("la liste de stagiaire ordonné a bien été recupérée");
		return listIntern;
	}

}
