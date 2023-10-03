package ru.practicum.mainserver.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.api.user.dto.NewUserRequest;
import ru.practicum.mainserver.api.user.dto.UserDto;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<UserDto> getUsers(List<Long> ids, Pageable page) {
        return null;
    }

    @Override
    public UserDto registerUser(NewUserRequest body) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
