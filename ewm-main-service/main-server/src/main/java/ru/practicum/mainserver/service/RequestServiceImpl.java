package ru.practicum.mainserver.service;

import org.springframework.stereotype.Service;
import ru.practicum.mainserver.api.event.dto.EventFullDto;
import ru.practicum.mainserver.api.event.dto.UpdateEventUserRequest;
import ru.practicum.mainserver.api.request.ParticipationRequestDto;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addParticipationRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }

    @Override
    public EventFullDto updateForEvent(Long userId, Long eventId, UpdateEventUserRequest body) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getByEvent(Long userId, Long eventId) {
        return null;
    }
}
