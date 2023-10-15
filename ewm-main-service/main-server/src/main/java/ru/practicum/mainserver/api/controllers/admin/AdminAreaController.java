package ru.practicum.mainserver.api.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.AreaDto;
import ru.practicum.mainserver.api.dao.mapper.AreaMapper;
import ru.practicum.mainserver.api.dao.mapper.LocationMapper;
import ru.practicum.mainserver.api.utils.validation.Marker;
import ru.practicum.mainserver.repository.entity.AreaEntity;
import ru.practicum.mainserver.repository.entity.LocationEntity;
import ru.practicum.mainserver.service.AreaService;
import ru.practicum.mainserver.service.LocationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/area")
@RequiredArgsConstructor
public class AdminAreaController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final AreaMapper areaMapper;
    private final AreaService areaService;


    @PostMapping
    @Validated(Marker.OnCreate.class)
    @ResponseStatus(HttpStatus.CREATED)
    public AreaDto createArea(@Valid @RequestBody AreaDto body) {
        log.debug("Received request from admin to create area: {}",
                body);
        LocationEntity location = locationService.save(
                locationMapper.entityFromDto(body.getLocation()));
        AreaEntity areaToCreate = areaMapper.entityFromDto(body, location);

        AreaEntity createdArea = areaService.addNew(areaToCreate);
        return areaMapper.dtoFromEntity(createdArea,
                locationMapper.dtoFromEntity(createdArea.getLocation()), null);
    }

    @PatchMapping("/{areaId}")
    @Validated(Marker.OnUpdate.class)
    public AreaDto patchArea(@PathVariable("areaId") @Positive Long areaId,
                             @Valid @RequestBody AreaDto body) {
        log.debug("Received request from admin to update area with id: {}; New data: {}",
                areaId, body);
        LocationEntity location = Optional.ofNullable(body.getLocation())
                .map(locDto -> locationService.save(
                        locationMapper.entityFromDto(locDto)))
                .orElse(null);

        AreaEntity updatedArea = areaService.update(areaId, body, location);
        return areaMapper.dtoFromEntity(updatedArea,
                locationMapper.dtoFromEntity(updatedArea.getLocation()), null);
    }

    @DeleteMapping("/{areaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("areaId") @Positive Long areaId) {
        log.debug("Received request from admin to delete area with id: {}",
                areaId);
        areaService.delete(areaId);
    }

}
