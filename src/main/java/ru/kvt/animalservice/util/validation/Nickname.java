package ru.kvt.animalservice.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NicknameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Nickname {

    String message() default "{Nickname}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
