package ru.practicum.mainserver.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.user.dto.NewUserRequest;
import ru.practicum.mainserver.api.user.dto.UserDto;
import ru.practicum.mainserver.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(value = "ids", required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        log.debug("Received request from admin to return users with ids: {},/n page: from {} with size {}",
                ids, from, size);
        Sort sort = Sort.by(DESC, "start");
        Pageable page = PageRequest.of(from / size, size, sort);
        return userService.getUsers(ids, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@Valid @RequestBody NewUserRequest body) {
        log.debug("Received request from admin to register user: {}",
                body);
        return userService.registerUser(body);
    }

    @DeleteMapping
    @ResponseStatus
    public void delete(@PathVariable("userId") Long userId) {
        log.debug("Received request from admin to delete user with id: {}",
                userId);
        userService.deleteUser(userId);
    }


}
