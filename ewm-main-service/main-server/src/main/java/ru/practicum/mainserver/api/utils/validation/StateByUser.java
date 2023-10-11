package ru.practicum.mainserver.api.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StateUserValidator.class)
public @interface StateByUser {
    String message() default "должно содержать дату, которая еще не наступила.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
