package ru.practicum.mainserver.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.api.dao.dto.UserDto;
import ru.practicum.mainserver.api.dao.mapper.UserMapper;
import ru.practicum.mainserver.api.utils.exception.NotFoundException;
import ru.practicum.mainserver.repository.UserRepository;
import ru.practicum.mainserver.repository.entity.QUserEntity;
import ru.practicum.mainserver.repository.entity.UserEntity;
import ru.practicum.mainserver.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final UserRepository repository;

    @Override
    public UserEntity registerUser(UserDto body) {
        UserEntity user = repository.save(mapper.entityFromDto(body));
        log.debug("{} - successfully saved", user);
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        repository.deleteById(userId);
        log.debug("User with id: {} - successfully deleted", userId);
    }

    @Override
    public List<UserEntity> getUsers(List<Long> ids, Integer from, Integer size) {
        BooleanExpression byIds = (ids != null) ?
                QUserEntity.userEntity.id.in(ids) :
                Expressions.TRUE.isTrue();
        Pageable pageRequest = PageRequest.of(from / size, size);
        List<UserEntity> foundUsers = repository.findAll(byIds, pageRequest).toList();
        log.debug("Found users with parameters: ids - {}, from - {}, size - {}{}",
                ids, from, size, foundUsers);
        return foundUsers;
    }

    @Override
    public UserEntity getById(Long userId) {
        return repository.findById(userId).orElseThrow(() ->
                new NotFoundException(UserEntity.class, userId));
    }

}
