package ru.practicum.mainserver.service;

import ru.practicum.mainserver.api.dao.dto.event.InputEventDto;
import ru.practicum.mainserver.api.utils.EventParameters;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.LocationEntity;

import java.util.List;
import java.util.Map;


public interface EventService {
    EventEntity getShortByInitiator(Long userId, Long eventId);

    List<EventEntity> getShortsByInitiator(Long userId, Integer from, Integer size);

    EventEntity addNew(EventEntity body);

    EventEntity updateByUser(Long userId, Long eventId, InputEventDto body, LocationEntity location);

    EventEntity updateByAdmin(Long eventId, InputEventDto body, LocationEntity newLocation);

    List<Long> getIdsByParams(EventParameters parameters);

    List<EventEntity> getShortSortedByViews(Map<Long, Long> viewsMap, Integer from, Integer size);

    List<EventEntity> getFullByParamsSortedById(EventParameters parameters, Integer from, Integer size);

    List<EventEntity> getShortByParamsSortedByEventDate(EventParameters eventParameters, Integer from, Integer size);

    EventEntity getPublic(Long id);

    List<EventEntity> getByIds(List<Long> eventIds);

    EventEntity getByID(Long eventId);

    List<EventEntity> findAllForArea(LocationEntity location, float radius, int from, int size);
}
