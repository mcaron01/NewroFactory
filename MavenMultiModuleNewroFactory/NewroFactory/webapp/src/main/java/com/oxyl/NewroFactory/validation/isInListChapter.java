package com.oxyl.NewroFactory.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ChapterIdValidator.class)  
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME) 
public @interface isInListChapter {
	String message() default "Le chapitre existe déjà !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
