package ru.practicum.mainserver.service;

import ru.practicum.mainserver.api.event.dto.*;
import ru.practicum.mainserver.api.event.utils.EventParameters;
import ru.practicum.mainserver.repository.entity.EventEntity;

import java.util.List;


public interface EventService {
    EventFullDto getByUser(Long userId, Long eventId);

    List<EventShortDto> getByUser(Long userId, Integer from, Integer size);

    EventFullDto addByUser(Long userId, NewEventDto body);

    EventRequestStatusUpdateResult updateByUser(Long userId, Long eventId, EventRequestStatusUpdateRequest body);

    List<EventShortDto> getPublic(EventParameters eventParameters, Integer from, Integer size);

    List<EventEntity> getByAdmin(EventParameters parameters, Integer from, Integer size);

    EventFullDto updateByAdmin(Long eventId, UpdateEventAdminRequest body);

    EventFullDto getPublic(Long id);
}
