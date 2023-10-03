package ru.practicum.mainserver.service;

import ru.practicum.mainserver.api.event.dto.EventFullDto;
import ru.practicum.mainserver.api.event.dto.UpdateEventUserRequest;
import ru.practicum.mainserver.api.request.ParticipationRequestDto;

import java.util.List;


public interface RequestService {
    List<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto addParticipationRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    EventFullDto updateForEvent(Long userId, Long eventId, UpdateEventUserRequest body);

    List<ParticipationRequestDto> getByEvent(Long userId, Long eventId);
}
