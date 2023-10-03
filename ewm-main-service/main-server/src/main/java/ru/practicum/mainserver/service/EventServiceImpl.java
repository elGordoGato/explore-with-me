package ru.practicum.mainserver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.api.event.dto.*;
import ru.practicum.mainserver.api.event.utils.EventParameters;
import ru.practicum.mainserver.repository.entity.EventEntity;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    @Override
    public EventFullDto getByUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public List<EventShortDto> getByUser(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto addByUser(Long userId, NewEventDto body) {
        return null;
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateByUser(Long userId, Long eventId, EventRequestStatusUpdateRequest body) {
        return null;
    }

    @Override
    public List<EventShortDto> getPublic(EventParameters eventParameters, Integer from, Integer size) {
        return null;
    }

    @Override
    public List<EventEntity> getByAdmin(EventParameters parameters, Integer from, Integer size) {


        return null;
    }

    @Override
    @Transactional
    public EventFullDto updateByAdmin(Long eventId, UpdateEventAdminRequest body) {
        return null;
    }

    @Override
    public EventFullDto getPublic(Long id) {
        return null;
    }
}
