package ru.practicum.mainserver.api.controllers.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.AreaDto;
import ru.practicum.mainserver.api.dao.dto.LocationDto;
import ru.practicum.mainserver.api.dao.dto.event.EventShortDto;
import ru.practicum.mainserver.api.dao.mapper.AreaMapper;
import ru.practicum.mainserver.api.dao.mapper.LocationMapper;
import ru.practicum.mainserver.api.utils.EventFiller;
import ru.practicum.mainserver.repository.entity.AreaEntity;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.service.AreaService;
import ru.practicum.mainserver.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/area")
@RequiredArgsConstructor
public class PublicAreaController {
    private final LocationMapper locationMapper;
    private final AreaMapper areaMapper;
    private final AreaService areaService;
    private final EventService eventService;
    private final EventFiller eventFiller;

    @GetMapping
    public List<AreaDto> getAll(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        log.debug("Received public request to return all areas,\nPage: from {} with size {}",
                from, size);
        List<AreaEntity> areas = areaService.findAll();
        Map<Long, LocationDto> locationMap = locationMapper.dtoMapFromEntityList(
                areaMapper.getLocationEntityList(areas));
        return areaMapper.dtoFromEntityList(areas, locationMap);
    }


    @GetMapping("/{areaId}")
    public AreaDto getById(@PathVariable("areaId") Long areaId,
                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                           @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        log.debug("Received public request get area with id: {} with events paged: from - {}, size - {}",
                areaId, from, size);
        AreaEntity area = areaService.getById(areaId);

        List<EventEntity> eventsInArea = eventService.findAllForArea(area.getLocation(), area.getRadius(), from, size);
        List<EventShortDto> eventShortsForArea = eventFiller.getEventShorts(eventsInArea, null);
        LocationDto location = locationMapper.dtoFromEntity(area.getLocation());

        return areaMapper.dtoFromEntity(area, location, eventShortsForArea);
    }
}
