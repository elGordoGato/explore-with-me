package ru.practicum.mainserver.api.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.dao.dto.CategoryDto;
import ru.practicum.mainserver.api.dao.dto.LocationDto;
import ru.practicum.mainserver.api.dao.dto.event.EventFullDto;
import ru.practicum.mainserver.api.dao.dto.event.EventShortDto;
import ru.practicum.mainserver.api.dao.dto.user.UserShortDto;
import ru.practicum.mainserver.api.dao.mapper.CategoryMapper;
import ru.practicum.mainserver.api.dao.mapper.EventMapper;
import ru.practicum.mainserver.api.dao.mapper.LocationMapper;
import ru.practicum.mainserver.api.dao.mapper.UserMapper;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.service.RequestService;
import ru.practicum.mainserver.service.StatService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventFiller {
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final RequestService requestService;
    private final UserMapper userMapper;
    private final LocationMapper locationMapper;
    private final StatService statService;

    public EventFullDto getEventFullDto(EventEntity updatedEvent) {
        CategoryDto categoryDto = categoryMapper.dtoFromEntity(
                updatedEvent.getCategory());
        Long confirmedRequest = requestService.getConfirmedRequestForEvent(
                updatedEvent.getId());
        UserShortDto userShortDto = userMapper.shortDtoFromEntity(
                updatedEvent.getInitiator());
        LocationDto locationDto = locationMapper.dtoFromEntity(
                updatedEvent.getLocation());
        Long views = statService.getViews(
                updatedEvent.getId());
        return eventMapper.fullDtoFromEntity(
                updatedEvent,
                categoryDto,
                confirmedRequest,
                userShortDto,
                locationDto,
                views);
    }

    public EventFullDto getEventFullDto(EventEntity createdEvent, long views, long confirmedRequests) {
        CategoryDto categoryDto = categoryMapper.dtoFromEntity(
                createdEvent.getCategory());
        UserShortDto userShortDto = userMapper.shortDtoFromEntity(
                createdEvent.getInitiator());
        LocationDto locationDto = locationMapper.dtoFromEntity(
                createdEvent.getLocation());

        return eventMapper.fullDtoFromEntity(
                createdEvent,
                categoryDto,
                confirmedRequests,
                userShortDto,
                locationDto,
                views);
    }

    public List<EventFullDto> getFullDtoList(List<EventEntity> events) {
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

    public List<EventShortDto> getEventShorts(List<EventEntity> events, Map<Long, Long> viewsMap) {
        return eventMapper.dtoFromEntityList(
                events,
                categoryMapper.dtoFromEntityMap(
                        eventMapper.getCategoryEntityMap(events)),
                requestService.getConfirmedRequestForEvents(
                        eventMapper.getEventsIds(events)),
                userMapper.dtoFromEntityMap(
                        eventMapper.getUserEntityMap(events)),
                Optional.ofNullable(viewsMap)
                        .orElse(statService.getMap(
                                eventMapper.getEventsIds(events))));
    }


}
