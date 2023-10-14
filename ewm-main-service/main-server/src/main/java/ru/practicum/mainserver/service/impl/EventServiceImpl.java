package ru.practicum.mainserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.api.dao.dto.event.InputEventDto;
import ru.practicum.mainserver.api.utils.EventParameters;
import ru.practicum.mainserver.api.utils.exception.ForbiddenException;
import ru.practicum.mainserver.api.utils.exception.NotFoundException;
import ru.practicum.mainserver.repository.EventRepository;
import ru.practicum.mainserver.repository.entity.CategoryEntity;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.LocationEntity;
import ru.practicum.mainserver.service.EventService;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.ZoneOffset.UTC;
import static ru.practicum.mainserver.api.utils.EventStateEnum.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;

    @Override
    public EventEntity getShortByInitiator(Long userId, Long eventId) {
        EventEntity foundEvent = repository.findShortByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(EventEntity.class, eventId));
        log.debug("Found event with id {} of initiator with id: {}.\n{}",
                eventId, userId, foundEvent);
        return foundEvent;
    }

    @Override
    public List<EventEntity> getShortsByInitiator(Long userId, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of(from / size, size);
        List<EventEntity> foundEvents = repository.findShortByInitiatorId(userId, pageRequest);
        log.debug("Found events of initiator with id: {}. Page: from {}, size: {}\n {}",
                userId, from, size, foundEvents);
        return foundEvents;
    }

    @Override
    @Transactional
    public EventEntity addNew(EventEntity event) {
        EventEntity createdEvent = repository.save(event);
        log.debug("{} - successfully created", createdEvent);
        return createdEvent;
    }

    @Override
    @Transactional
    public EventEntity updateByUser(Long userId, Long eventId, InputEventDto body, LocationEntity location) {
        EventEntity eventToBeUpdated = repository.findFullById(eventId)
                .orElseThrow(() ->
                        new NotFoundException(EventEntity.class, eventId));

        if (!eventToBeUpdated.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("User has no rights to update this event");
        }
        EventEntity updatedEvent = updateEvent(eventToBeUpdated, body, location);
        log.debug("Event updated: {}", updatedEvent);
        return updatedEvent;
    }

    @Override
    @Transactional
    public EventEntity updateByAdmin(Long eventId, InputEventDto body, LocationEntity newLocation) {
        EventEntity eventToBeUpdated = repository.findFullById(eventId)
                .orElseThrow(() ->
                        new NotFoundException(EventEntity.class, eventId));
        if (!eventToBeUpdated.getState().equals(PENDING)) {
            throw new ForbiddenException("Only pending events can be changed");
        }
        EventEntity updatedEvent = updateEvent(eventToBeUpdated, body, newLocation);
        log.debug("Event updated: {}", updatedEvent);
        return updatedEvent;
    }


    @Override
    public List<EventEntity> getFullByParamsSortedById(EventParameters parameters, Integer from, Integer size) {
        return repository.findEventsByParams(true, parameters, from, size);
    }

    @Override
    public List<Long> getIdsByParams(EventParameters parameters) {
        return repository.findIdsByParams(parameters);
    }

    @Override
    public EventEntity getPublic(Long id) {
        return repository.findByIdAndState(id, PUBLISHED)
                .orElseThrow(() -> new NotFoundException(EventEntity.class, id));
    }

    @Override
    public List<EventEntity> getShortSortedByViews(Map<Long, Long> viewsMap, Integer from, Integer size) {
        List<Long> sortedIds = viewsMap.keySet().stream()
                .sorted(Comparator.comparingLong(viewsMap::get))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
        return repository.findShortByIdIn(sortedIds);
    }


    @Override
    public List<EventEntity> getShortByParamsSortedByEventDate(EventParameters eventParameters,
                                                               Integer from,
                                                               Integer size) {
        return repository.findEventsByParams(false, eventParameters, from, size);
    }

    @Override
    public List<EventEntity> getByIds(List<Long> eventIds) {
        return repository.findByIdIn(eventIds);
    }

    @Override
    public EventEntity getByID(Long eventId) {
        return repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EventEntity.class, eventId));
    }


    private EventEntity updateEvent(EventEntity eventToBeUpdated,
                                    InputEventDto body,
                                    LocationEntity newLocation) {
        if (eventToBeUpdated.getState().equals(PUBLISHED)) {
            throw new ForbiddenException("Only pending or canceled events can be changed");
        }
        Optional.ofNullable(body.getTitle())
                .ifPresent(eventToBeUpdated::setTitle);
        Optional.ofNullable(body.getEventDate())
                .ifPresent(eventDate ->
                        eventToBeUpdated.setEventDate(
                                eventDate.toInstant(UTC)));
        Optional.ofNullable(body.getPaid())
                .ifPresent(eventToBeUpdated::setPaid);
        Optional.ofNullable(body.getDescription())
                .ifPresent(eventToBeUpdated::setDescription);
        Optional.ofNullable(body.getAnnotation())
                .ifPresent(eventToBeUpdated::setAnnotation);
        Optional.ofNullable(body.getCategory())
                .ifPresent(categoryId ->
                        eventToBeUpdated.setCategory(
                                new CategoryEntity(categoryId, null)));
        Optional.ofNullable(newLocation)
                .ifPresent(eventToBeUpdated::setLocation);
        Optional.ofNullable(body.getParticipantLimit())
                .ifPresent(eventToBeUpdated::setParticipantLimit);
        Optional.ofNullable(body.getRequestModeration())
                .ifPresent(eventToBeUpdated::setRequestModeration);
        Optional.ofNullable(body.getStateAction())
                .ifPresent(stateAction -> updateEventState(
                        stateAction, eventToBeUpdated, eventToBeUpdated.getParticipantLimit()));

        return eventToBeUpdated;
    }

    private void updateEventState(InputEventDto.StateActionEnum stateAction, EventEntity event, int partLimit) {
        switch (stateAction) {
            case REJECT_EVENT:
            case CANCEL_REVIEW:
                event.setState(CANCELED);
                break;
            case PUBLISH_EVENT:
                event.setState(PUBLISHED);
                event.setPublishedOn(Instant.now());
                break;
            case SEND_TO_REVIEW:
                if (partLimit == 0) {
                    event.setState(PUBLISHED);
                    event.setPublishedOn(Instant.now());
                } else {
                    event.setState(PENDING);
                }
                break;
        }
    }
}

