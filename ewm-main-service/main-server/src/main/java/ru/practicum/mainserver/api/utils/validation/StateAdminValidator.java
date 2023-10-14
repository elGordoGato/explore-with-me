package ru.practicum.mainserver.api.utils.validation;

import ru.practicum.mainserver.api.dao.dto.event.InputEventDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateAdminValidator implements ConstraintValidator<StateByAdmin, InputEventDto.StateActionEnum> {

    @Override
    public boolean isValid(InputEventDto.StateActionEnum stateAction, ConstraintValidatorContext context) {
        return InputEventDto.StateActionEnum.PUBLISH_EVENT.equals(stateAction) ||
                InputEventDto.StateActionEnum.REJECT_EVENT.equals(stateAction) ||
                stateAction == null;
    }


}
