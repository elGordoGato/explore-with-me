package ru.practicum.mainserver.service;

import ru.practicum.mainserver.api.utils.StatusEnum;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.RequestEntity;

import java.util.List;
import java.util.Map;


public interface RequestService {
    List<RequestEntity> getUserRequests(Long userId);

    RequestEntity addParticipationRequest(RequestEntity newRequest);

    RequestEntity cancelRequest(Long userId, Long requestId);

    List<RequestEntity> getByEvent(Long eventId, Long userId);

    Map<Long, Long> getConfirmedRequestForEvents(List<Long> eventIds);

    Long getConfirmedRequestForEvent(Long eventId);


    Map<String, List<RequestEntity>> updateRequestsByInitiator(Long userId, EventEntity eventId, List<Long> requestsId, StatusEnum newStatus);
}
