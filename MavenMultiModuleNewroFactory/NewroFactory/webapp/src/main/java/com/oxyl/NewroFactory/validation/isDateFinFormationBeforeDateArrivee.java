package com.oxyl.NewroFactory.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DateFinFormationValidator.class)  
@Target( { ElementType.TYPE } )  
@Retention(RetentionPolicy.RUNTIME)  
public @interface isDateFinFormationBeforeDateArrivee {
    //error message  
    public String message() default "Veuillez entrer une date de fin de formation valide, la date de fin de formation doit être avant la date d'arrivée";  
//represents group of constraints     
    public Class<?>[] groups() default {};  
//represents additional information about annotation  
    public Class<? extends Payload>[] payload() default {};  
}

