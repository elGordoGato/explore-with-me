package ru.practicum.mainserver.api.user;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.category.CategoryDto;
import ru.practicum.mainserver.api.user.dto.UserDto;
import ru.practicum.mainserver.repository.entity.CategoryEntity;
import ru.practicum.mainserver.repository.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserEntity entityFromDto(UserDto dto) {
        return new UserEntity(null, dto.getName(), dto.getEmail());
    }

    public UserDto dtoFromEntity(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

    public List<UserDto> dtoFromEntityList(List<UserEntity> userEntities) {
        return userEntities.stream().map(this::dtoFromEntity).collect(Collectors.toList());
    }

}
