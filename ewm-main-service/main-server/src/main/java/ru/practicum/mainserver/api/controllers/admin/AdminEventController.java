package ru.practicum.mainserver.api.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.*;
import ru.practicum.mainserver.api.dao.mapper.CategoryMapper;
import ru.practicum.mainserver.api.dao.mapper.EventMapper;
import ru.practicum.mainserver.api.dao.mapper.LocationMapper;
import ru.practicum.mainserver.api.dao.mapper.UserMapper;
import ru.practicum.mainserver.api.utils.EventParameters;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.LocationEntity;
import ru.practicum.mainserver.service.EventService;
import ru.practicum.mainserver.service.LocationService;
import ru.practicum.mainserver.service.RequestService;
import ru.practicum.mainserver.service.StatService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final RequestService requestService;
    private final UserMapper userMapper;
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final StatService statService;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(value = "users", required = false) List<Long> users,
                                        @RequestParam(value = "states", required = false) List<String> states,
                                        @RequestParam(value = "categories", required = false) List<Long> categories,
                                        @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        EventParameters parameters = eventMapper.getParams(null,
                users, states, categories, null, rangeStart, rangeEnd, null);
        log.debug("Received request from admin to get events with parameters: {}", parameters);
        List<EventEntity> foundEvents = eventService.getFullByParamsSortedById(parameters, from, size);
        return getFullDtos(foundEvents);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable("eventId") Long eventId, @RequestBody @Valid InputDto body) {
        log.debug("Received request from admin to update event with id: {},\n new data: {}", eventId, body);
        LocationEntity newLocation = body.getLocationDto() != null ?
                locationService.save(
                        locationMapper.entityFromDto(
                                body.getLocationDto())) :
                null;
        EventEntity updatedEvent = eventService.updateByAdmin(eventId, body, newLocation);
        return getEventFullDto(updatedEvent);
    }

    private EventFullDto getEventFullDto(EventEntity updatedEvent) {
        CategoryDto categoryDto = categoryMapper.dtoFromEntity(updatedEvent.getCategory());
        Long confirmedRequest = requestService.getConfirmedRequestForEvent(updatedEvent.getId());
        UserShortDto userShortDto = userMapper.shortDtoFromEntity(updatedEvent.getInitiator());
        LocationDto locationDto = locationMapper.dtoFromEntity(updatedEvent.getLocation());
        Long views = statService.getViews(updatedEvent.getId());
        return eventMapper.fullDtoFromEntity(
                updatedEvent,
                categoryDto,
                confirmedRequest,
                userShortDto,
                locationDto,
                views);
    }

    private List<EventFullDto> getFullDtos(List<EventEntity> events) {
        return eventMapper.dtoFromEntityList(
                events,
                categoryMapper.dtoFromEntityMap(
                        eventMapper.getCategoryEntityMap(events)),
                requestService.getConfirmedRequestForEvents(
                        eventMapper.getEventsIds(events)),
                userMapper.dtoFromEntityMap(
                        eventMapper.getUserEntityMap(events)),
                locationMapper.dtoFromEntityMap(
                        eventMapper.getLocationEntityMap(events)),
                statService.getMap(
                        eventMapper.getEventsIds(events))
        );
    }
}
