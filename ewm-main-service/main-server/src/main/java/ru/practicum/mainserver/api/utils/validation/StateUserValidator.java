package ru.practicum.mainserver.api.utils.validation;

import ru.practicum.mainserver.api.dao.dto.InputDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class StateUserValidator implements ConstraintValidator<StateByUser, InputDto.StateActionEnum> {

    @Override
    public boolean isValid(InputDto.StateActionEnum stateAction, ConstraintValidatorContext context) {
        return InputDto.StateActionEnum.SEND_TO_REVIEW.equals(stateAction) ||
                InputDto.StateActionEnum.CANCEL_REVIEW.equals(stateAction);
    }


}
