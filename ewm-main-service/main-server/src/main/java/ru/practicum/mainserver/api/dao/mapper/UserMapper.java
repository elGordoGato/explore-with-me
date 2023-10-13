package ru.practicum.mainserver.api.dao.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.dao.dto.user.UserDto;
import ru.practicum.mainserver.api.dao.dto.user.UserShortDto;
import ru.practicum.mainserver.repository.entity.UserEntity;

import java.util.List;
import java.util.Map;
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
        return userEntities.stream()
                .map(this::dtoFromEntity)
                .collect(Collectors.toList());
    }

    public Map<Long, UserShortDto> dtoFromEntityMap(Map<Long, UserEntity> userEntityMap) {
        return userEntityMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> shortDtoFromEntity(
                                e.getValue())));
    }

    public UserShortDto shortDtoFromEntity(UserEntity entity) {
        return UserShortDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

}
