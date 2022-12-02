package com.oxyl.NewroFactory.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UsernameValidator.class)  
@Target( { ElementType.FIELD } )  
@Retention(RetentionPolicy.RUNTIME) 
public @interface IsNotInDb {
	String message() default "L'utilisateur existe déjà";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
