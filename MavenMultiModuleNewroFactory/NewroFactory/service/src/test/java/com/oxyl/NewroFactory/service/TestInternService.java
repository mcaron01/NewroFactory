package com.oxyl.NewroFactory.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.data.domain.PageRequest;

import com.oxyl.NewroFactory.exception.InternException;
import com.oxyl.NewroFactory.model.Intern;
import com.oxyl.NewroFactory.model.Intern.InternBuilder;
import com.oxyl.NewroFactory.model.Promotion.PromotionBuilder;
import com.oxyl.NewroFactory.model.Promotion;
import com.oxyl.NewroFactory.persistence.InternDao;
import com.oxyl.NewroFactory.service.InternService;

/**
 * Unit test for simple App.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class TestInternService 
{
	Intern intern;
	InternService internService;
	InternDao internDao;
	
	List<Intern> interns = new ArrayList<>();
	Optional<Intern> optIntern = Optional.<Intern>empty();
	
	@BeforeAll
	void setUp() throws Exception{
		internDao = mock(InternDao.class);
		internService = new InternService(internDao);
		Constructor<InternBuilder> buildIntern = InternBuilder.class.getDeclaredConstructor(new Class[] {String.class,String.class,LocalDate.class,Promotion.class});
		Constructor<PromotionBuilder> buildPromotion = PromotionBuilder.class.getDeclaredConstructor(new Class[] {Integer.class,String.class});
		Promotion promotion = buildPromotion.newInstance(1,"Septembre 2022").build();
		interns.add(buildIntern.newInstance("Abel","Boulle",LocalDate.of(2022,9,1),promotion).id(1).build());
		interns.add(buildIntern.newInstance("Abélard","Grandjean",LocalDate.of(2022,9,1),promotion).id(2).build());
		interns.add(buildIntern.newInstance("Abelin","Bourguignon",LocalDate.of(2022,9,1),promotion).id(3).build());
		interns.add(buildIntern.newInstance("Abraham","Delannoy",LocalDate.of(2022,9,1),promotion).id(4).build());
		interns.add(buildIntern.newInstance("Achille","Charbonnier",LocalDate.of(2022,9,1),promotion).id(5).build());
		intern = buildIntern.newInstance("Septembre","Septembre",LocalDate.of(2022,9,1),promotion).build();
		
		
	}

    @Test
    public void shouldReturnList() throws InternException
    {
        when(internDao.getListOfIntern(PageRequest.of(1, 5))).thenReturn(interns);
        List<Intern> models = internService.getListIntern(PageRequest.of(1, 5));
        assertTrue(models.equals(interns));
    }
    
    @Test
    public void shouldReturnIntern() throws InternException {
    	when(internDao.getById(1)).thenReturn(optIntern);
    	Optional<Intern> model = internService.getById(1);
    }
    
    @Test
    public void shouldCreateIntern() throws InternException {
    	doNothing().when(internDao).create(intern);
    	internService.create(intern);
    	verify(internDao,times(1)).create(intern);
    }
    
    @Test
    public void shouldEditIntern() throws InternException {
    	doNothing().when(internDao).edit(intern);
    	internService.edit(1, intern);
    	verify(internDao,times(1)).edit(intern);
    }
    
    @Test
    public void shouldDeleteIntern() throws InternException {
    	doNothing().when(internDao).delete(1);
    	internService.delete(1);
    	verify(internDao, times(1)).delete(1);
    }
    
    @Test
    public void shoudlReturnCount() throws InternException {
    	when(internDao.count("Septembre 2022")).thenReturn((long) interns.size());
    	long count = internService.getNbIntern("Septembre 2022");
    	assertTrue(count == interns.size());
    }
    
    @Test
    public void shouldReturnSearchName() throws InternException {
    	when(internDao.getListOfInternByFirstNameOrLastName(PageRequest.of(1, 5), "", "Septembre 2022")).thenReturn(interns);
    	List<Intern> models = internService.getListInternByName(PageRequest.of(1, 5), "", "Septembre 2022");
    	assertTrue(models.equals(interns));
    }
    
    @Test
    public void shouldReturnOrderBy() throws InternException {
    	when(internDao.getListOfInternOrderBy(PageRequest.of(1, 5))).thenReturn(interns);
    	List<Intern> models = internService.getListInternOrderBy(PageRequest.of(1, 5));
    	assertTrue(models.equals(interns));
    }
}
