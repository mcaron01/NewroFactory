package com.oxyl.NewroFactory.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.oxyl.NewroFactory.dto.front.InternDto;

public class DateFinFormationValidator implements ConstraintValidator<isDateFinFormationBeforeDateArrivee,InternDto> {
	
	@Override
    public boolean isValid(InternDto internDto, ConstraintValidatorContext cvc) {  
    	if (internDto.getDateFinFormation() == null) {
    		return true;
    	}
    	
		if (internDto.getDateFinFormation() != null && internDto.getDateArrivee() != null) {
			return internDto.getDateArrivee().isBefore(internDto.getDateFinFormation());
		} else {
		return false;
		}
    }  
}

