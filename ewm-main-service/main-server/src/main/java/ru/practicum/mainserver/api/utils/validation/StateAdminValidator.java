package ru.practicum.mainserver.api.utils.validation;

import ru.practicum.mainserver.api.dao.dto.InputDto;
import ru.practicum.mainserver.api.utils.validation.StateByUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class StateAdminValidator implements ConstraintValidator<StateByAdmin, InputDto.StateActionEnum> {

    @Override
    public boolean isValid(InputDto.StateActionEnum stateAction, ConstraintValidatorContext context) {
        return InputDto.StateActionEnum.PUBLISH_EVENT.equals(stateAction) ||
                InputDto.StateActionEnum.REJECT_EVENT.equals(stateAction);
    }


}
