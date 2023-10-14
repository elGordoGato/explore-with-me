package ru.practicum.mainserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.api.utils.RequestStatusEnum;
import ru.practicum.mainserver.api.utils.exception.ForbiddenException;
import ru.practicum.mainserver.api.utils.exception.NotFoundException;
import ru.practicum.mainserver.repository.RequestRepository;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.RequestEntity;
import ru.practicum.mainserver.service.RequestService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.mainserver.api.utils.EventStateEnum.PUBLISHED;
import static ru.practicum.mainserver.api.utils.RequestStatusEnum.CONFIRMED;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository repository;

    @Override
    public List<RequestEntity> getUserRequests(Long userId) {
        List<RequestEntity> foundRequests = repository.findByRequester_Id(userId);
        log.debug("Found requests of user with id {}: {}", userId, foundRequests);
        return foundRequests;
    }

    @Override
    @Transactional
    public RequestEntity addParticipationRequest(RequestEntity newRequest) {
        if (newRequest.getRequester().getId().equals(newRequest.getEvent().getInitiator().getId())) {
            throw new ForbiddenException("Initiator can not create participation request");
        }
        if (!newRequest.getEvent().getState().equals(PUBLISHED)) {
            throw new ForbiddenException("Event should be published to create participation request in it");
        }
        Long confirmedCount = getConfirmedRequestForEvent(
                newRequest.getEvent().getId());
        log.debug("part size: {} and event: \n{}", confirmedCount, newRequest.getEvent());
        if (newRequest.getEvent().getParticipantLimit() != 0
                && confirmedCount >= newRequest.getEvent().getParticipantLimit()) {
            throw new ForbiddenException(
                    "Event already reached participation limit to create participation request in it");
        }
        RequestEntity savedRequest = repository.save(newRequest);
        log.debug("Request successfully saved! - {}", savedRequest);
        return savedRequest;
    }


    @Override
    @Transactional
    public RequestEntity cancelRequest(Long userId, Long requestId) {
        RequestEntity requestToCancel = repository.findById(requestId).orElseThrow(() ->
                new NotFoundException(RequestEntity.class, requestId));
        if (!requestToCancel.getRequester().getId().equals(userId)) {
            throw new ForbiddenException("User has no rights to cancel this request");
        }
        requestToCancel.setStatus(RequestStatusEnum.CANCELED);
        return requestToCancel;
    }

    @Override
    public Map<Long, Long> getConfirmedRequestForEvents(List<Long> eventIds) {
        List<RequestEntity> confirmedRequestsList = repository.findByEvent_IdInAndStatus(
                eventIds, CONFIRMED);
        Map<Long, Long> confirmedRequestsMap = confirmedRequestsList.stream()
                .collect(Collectors.groupingBy(
                        request -> request.getEvent().getId(),
                        Collectors.counting()));
        log.debug("Found confirmed requests for events with Id in: {}\n{}",
                eventIds, confirmedRequestsList);
        return confirmedRequestsMap;
    }

    @Override
    public Long getConfirmedRequestForEvent(Long eventId) {
        Long numberOfConfirmedRequests = repository.countByEvent_IdAndStatus(
                eventId, CONFIRMED);
        log.debug("{} confirmed requests for event with id: {}",
                numberOfConfirmedRequests, eventId);
        return numberOfConfirmedRequests;
    }

    @Override
    @Transactional
    public Map<String, List<RequestEntity>> updateRequestsByInitiator(Long userId,
                                                                      EventEntity event,
                                                                      List<Long> requestsId,
                                                                      RequestStatusEnum newStatus) {
        List<RequestEntity> allEventRequests = repository.findByEvent_Id(event.getId());
        Long confirmedRequests = getConfirmedRequestForEvent(event.getId());
        if (event.getParticipantLimit() - confirmedRequests - requestsId.size() < 0) {
            throw new ForbiddenException("Limit of confirmed participation requests for event already reached");
        }
        List<RequestEntity> updatedRequests = allEventRequests.stream()
                .filter(requestEntity ->
                        requestsId.contains(requestEntity.getId()))
                .collect(Collectors.toList());
        for (RequestEntity updatedRequest : updatedRequests) {
            if (updatedRequest.getStatus().equals(CONFIRMED)) {
                throw new ForbiddenException("Not allowed to change status of already confirmed requests");
            }
            updatedRequest.setStatus(newStatus);
        }
        allEventRequests.removeAll(repository.saveAll(updatedRequests));
        List<RequestEntity> confirmed;
        List<RequestEntity> rejected;
        if (newStatus.equals(CONFIRMED)) {
            confirmed = updatedRequests;
            rejected = allEventRequests;
        } else {
            confirmed = allEventRequests;
            rejected = updatedRequests;
        }
        return Map.of("confirmedRequests", confirmed, "rejectedRequests", rejected);
    }

    @Override
    public List<RequestEntity> getByEvent(Long eventId, Long userId) {
        List<RequestEntity> foundRequests = repository.findByEvent_IdAndEvent_Initiator_Id(eventId, userId);
        log.debug("Found requests of user with id: {} for event with id: {}\n{}", userId, eventId, foundRequests);
        return foundRequests;
    }

}
