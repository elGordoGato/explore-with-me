package ru.practicum.mainserver.api.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.user.UserDto;
import ru.practicum.mainserver.api.dao.mapper.UserMapper;
import ru.practicum.mainserver.repository.entity.UserEntity;
import ru.practicum.mainserver.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(value = "ids", required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        log.debug("Received request from admin to return users with ids: {},\nPage: from {} with size {}",
                ids, from, size);
        List<UserEntity> users = userService.getUsers(ids, from, size);
        return userMapper.dtoFromEntityList(users);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@Valid @RequestBody UserDto body) {
        log.debug("Received request from admin to register user: {}",
                body);
        UserEntity userEntity = userService.registerUser(body);
        return userMapper.dtoFromEntity(userEntity);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("userId") Long userId) {
        log.debug("Received request from admin to delete user with id: {}",
                userId);
        userService.deleteUser(userId);
    }


}
