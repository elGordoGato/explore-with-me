package ru.practicum.mainserver.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.api.user.UserMapper;
import ru.practicum.mainserver.api.user.dto.UserDto;
import ru.practicum.mainserver.repository.UserRepository;
import ru.practicum.mainserver.repository.entity.CompilationEntity;
import ru.practicum.mainserver.repository.entity.QUserEntity;
import ru.practicum.mainserver.repository.entity.UserEntity;

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
        log.debug("Found compilations with parameters: ids - {}, from - {}, size - {}{}",
                ids, from, size, foundUsers);
        return foundUsers;
    }
}
