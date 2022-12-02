package com.oxyl.NewroFactory.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PromotionIdValidator.class)  
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME) 
public @interface isInListPromotion {
	String message() default "Veuillez selectionner une promotion existante !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
