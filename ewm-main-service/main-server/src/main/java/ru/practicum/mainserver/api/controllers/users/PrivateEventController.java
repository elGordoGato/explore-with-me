package ru.practicum.mainserver.api.controllers.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.*;
import ru.practicum.mainserver.api.dao.mapper.*;
import ru.practicum.mainserver.api.utils.StatusEnum;
import ru.practicum.mainserver.api.utils.validation.Marker;
import ru.practicum.mainserver.api.utils.validation.StateUserValidator;
import ru.practicum.mainserver.repository.entity.*;
import ru.practicum.mainserver.service.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final RequestService requestService;
    private final RequestMapper requestMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final StatService statService;

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable("userId") Long userId,
                                         @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        log.debug("Received request to get all user's events for user with id: {};\n" +
                        "Page: from - {}, size - {}",
                userId, from, size);
        List<EventEntity> userEvents = eventService.getShortsByInitiator(userId, from, size);

        Map<Long, CategoryDto> categoryDtoMap = categoryMapper.dtoFromEntityMap(
                eventMapper.getCategoryEntityMap(userEvents));
        Map<Long, UserShortDto> userShortDtoMap = userMapper.dtoFromEntityMap(
                eventMapper.getUserEntityMap(userEvents));
        Map<Long, Long> confirmedRequestsMap = requestService.getConfirmedRequestForEvents(
                eventMapper.getEventsIds(userEvents));
        Map<Long, Long> viewsMap = statService.getMap(
                eventMapper.getEventsIds(userEvents));
        return eventMapper.dtoFromEntityList(userEvents, categoryDtoMap, confirmedRequestsMap, userShortDtoMap, viewsMap);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(Marker.OnCreate.class)
    public EventFullDto addEvent(@PathVariable("userId") Long userId,
                                 @RequestBody @Valid InputDto body) {
        log.debug("Received request from user with id: {} to post event: {}",
                userId, body);

        UserEntity initiator = userService.getById(userId);
        LocationEntity location = locationService.save(
                locationMapper.entityFromDto(
                        body.getLocationDto()));
        CategoryEntity category = categoryService.get(body.getCategory());

        EventEntity eventToCreate = eventMapper.EntityFromDto(body, initiator, location, category);
        EventEntity createdEvent = eventService.addNew(eventToCreate);

        CategoryDto categoryDto = categoryMapper.dtoFromEntity(createdEvent.getCategory());
        Long confirmedRequest = requestService.getConfirmedRequestForEvent(createdEvent.getId());
        UserShortDto userShortDto = userMapper.shortDtoFromEntity(createdEvent.getInitiator());
        LocationDto locationDto = locationMapper.dtoFromEntity(createdEvent.getLocation());
        Long views = 0L;
        return eventMapper.fullDtoFromEntity(createdEvent,
                categoryDto,
                confirmedRequest,
                userShortDto,
                locationDto,
                views);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable("userId") Long userId,
                                 @PathVariable("eventId") Long eventId) {
        log.debug("Received request from user with id: {} to get event with id: {}",
                userId, eventId);
        EventEntity event = eventService.getShortByInitiator(userId, eventId);
        return getEventFullDto(event);
    }

    @PatchMapping("/{eventId}")
    @Validated({Marker.OnUpdate.class, StateUserValidator.class})
    public EventFullDto updateEventByUser(@PathVariable("userId") Long userId,
                                          @PathVariable("eventId") Long eventId,
                                          @RequestBody @Valid InputDto body) {
        log.debug("Received request from user with id: {} to update event with id: {}, new data: {}",
                userId, eventId, body);
        LocationEntity newLocation = body.getLocationDto() != null ?
                locationService.save(
                        locationMapper.entityFromDto(
                                body.getLocationDto())) :
                null;
        EventEntity updatedEvent = eventService.updateByUser(userId, eventId, body, newLocation);
        return getEventFullDto(updatedEvent);
    }


    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeRequestStatus(@PathVariable("userId") Long userId,
                                                              @PathVariable("eventId") Long eventId,
                                                              @RequestBody @Valid EventRequestStatusUpdateRequest body) {
        log.debug("Received request from user with id: {} to update event with id: {}, new data: {}",
                userId, eventId, body);
        List<Long> requestsId = body.getRequestIds();
        StatusEnum newStatus = body.getStatus();
        EventEntity event = eventService.getPublic(eventId);
        Map<String, List<RequestEntity>> updateResult = requestService.updateRequestsByInitiator(
                userId, event, requestsId, newStatus);
        return requestMapper.getStatusUpdateResult(updateResult);
    }


    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventParticipationRequests(@PathVariable("userId") Long userId,
                                                                       @PathVariable("eventId") Long eventId) {
        log.debug("Received request from user with id: {} to get participation requests for event with id: {}",
                userId, eventId);
        List<RequestEntity> foundRequests = requestService.getByEvent(eventId, userId);
        return requestMapper.dtoFromEntityList(foundRequests);
    }

    private EventFullDto getEventFullDto(EventEntity updatedEvent) {
        CategoryDto categoryDto = categoryMapper.dtoFromEntity(updatedEvent.getCategory());
        Long confirmedRequest = requestService.getConfirmedRequestForEvent(updatedEvent.getId());
        UserShortDto userShortDto = userMapper.shortDtoFromEntity(updatedEvent.getInitiator());
        LocationDto locationDto = locationMapper.dtoFromEntity(updatedEvent.getLocation());
        Long views = statService.getViews(updatedEvent.getId()); //TODO views
        return eventMapper.fullDtoFromEntity(
                updatedEvent,
                categoryDto,
                confirmedRequest,
                userShortDto,
                locationDto,
                views);
    }


}
