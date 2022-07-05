package ru.kvt.animalservice.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SpeciesValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Species {

    String message() default "{Species}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
