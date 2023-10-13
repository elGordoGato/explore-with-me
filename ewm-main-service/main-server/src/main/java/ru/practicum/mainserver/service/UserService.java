package ru.practicum.mainserver.service;

import ru.practicum.mainserver.api.dao.dto.user.UserDto;
import ru.practicum.mainserver.repository.entity.UserEntity;

import java.util.List;


public interface UserService {
    UserEntity registerUser(UserDto body);

    void deleteUser(Long userId);

    List<UserEntity> getUsers(List<Long> ids, Integer from, Integer size);

    UserEntity getById(Long userId);
}
