package ru.practicum.mainserver.api.utils.validation;

import ru.practicum.mainserver.api.dao.dto.event.InputEventDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateUserValidator implements ConstraintValidator<StateByUser, InputEventDto.StateActionEnum> {

    @Override
    public boolean isValid(InputEventDto.StateActionEnum stateAction, ConstraintValidatorContext context) {
        return InputEventDto.StateActionEnum.SEND_TO_REVIEW.equals(stateAction) ||
                InputEventDto.StateActionEnum.CANCEL_REVIEW.equals(stateAction) ||
                stateAction == null;
    }


}
