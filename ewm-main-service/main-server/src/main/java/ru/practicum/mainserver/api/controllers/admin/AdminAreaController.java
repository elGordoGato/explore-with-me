package ru.practicum.mainserver.api.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.AreaDto;
import ru.practicum.mainserver.api.dao.dto.user.UserDto;
import ru.practicum.mainserver.api.dao.mapper.AreaMapper;
import ru.practicum.mainserver.api.dao.mapper.LocationMapper;
import ru.practicum.mainserver.repository.entity.AreaEntity;
import ru.practicum.mainserver.repository.entity.UserEntity;
import ru.practicum.mainserver.service.LocationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/location")
@RequiredArgsConstructor
public class AdminAreaController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final AreaMapper areaMapper;

//    @GetMapping
//    public List<AreaDto> getAreas(@RequestParam(value = "ids", required = false) List<Long> ids,
//                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
//                                  @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
//        log.debug("Received request from admin to return areas with ids: {},\nPage: from {} with size {}",
//                ids, from, size);
//        List<UserEntity> users = userService.getUsers(ids, from, size);
//        return userMapper.dtoFromEntityList(users);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AreaDto createArea(@Valid @RequestBody AreaDto body) {
        log.debug("Received request from admin to create area: {}",
                body);
        AreaEntity userEntity = userService.registerUser(body);
        return areaMapper.dtoFromEntity(userEntity);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("userId") Long userId) {
        log.debug("Received request from admin to delete user with id: {}",
                userId);
        userService.deleteUser(userId);
    }


}
