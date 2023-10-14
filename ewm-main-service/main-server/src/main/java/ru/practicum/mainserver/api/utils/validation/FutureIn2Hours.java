package ru.practicum.mainserver.api.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateValidator.class)
public @interface FutureIn2Hours {
    String message() default "должно содержать дату, которая наступит минимум через 2 часа.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
