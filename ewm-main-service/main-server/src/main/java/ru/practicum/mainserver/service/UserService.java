package ru.practicum.mainserver.service;

import ru.practicum.mainserver.api.dao.dto.UserDto;
import ru.practicum.mainserver.repository.entity.UserEntity;

import java.util.List;
import java.util.Map;


public interface UserService {
    UserEntity registerUser(UserDto body);

    void deleteUser(Long userId);

    List<UserEntity> getUsers(List<Long> ids, Integer from, Integer size);

    Map<Long, UserEntity> getMapForEvents(List<Long> eventIds);

    UserEntity getById(Long userId);

    void checkExistance(Long userId);

    UserEntity check(Long userId);
}
