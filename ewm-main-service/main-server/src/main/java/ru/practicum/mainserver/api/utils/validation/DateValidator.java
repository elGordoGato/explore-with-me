package ru.practicum.mainserver.api.utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DateValidator implements ConstraintValidator<FutureIn2Hours, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        if (eventDate == null) {
            return true;
        } else {
            return LocalDateTime.now().plusHours(2).isBefore(eventDate);
        }
    }
}
