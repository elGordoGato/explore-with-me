package ru.practicum.mainserver.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.mainserver.api.user.dto.NewUserRequest;
import ru.practicum.mainserver.api.user.dto.UserDto;

import java.util.List;


public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Pageable page);

    UserDto registerUser(NewUserRequest body);

    void deleteUser(Long userId);
}
