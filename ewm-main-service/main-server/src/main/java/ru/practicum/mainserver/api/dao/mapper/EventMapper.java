package ru.practicum.mainserver.api.dao.mapper;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.practicum.mainserver.api.dao.dto.*;
import ru.practicum.mainserver.api.utils.EventParameters;
import ru.practicum.mainserver.repository.entity.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EventEntity EntityFromDto(InputDto dto, UserEntity initiator, LocationEntity location, CategoryEntity category) {
        EventEntity entity = new EventEntity();
        entity.setAnnotation(dto.getAnnotation());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setInitiator(initiator);
        entity.setCategory(category);
        entity.setEventDate(dto.getEventDate().toInstant(ZoneOffset.UTC));
        entity.setLocation(location);
        entity.setParticipantLimit(dto.getParticipantLimit());
        entity.setPaid(dto.getPaid());
        entity.setRequestModeration(dto.getRequestModeration() != null ? dto.getRequestModeration() : true);
        entity.setState(entity.getParticipantLimit() == 0 ? StateEnum.PUBLISHED : StateEnum.PENDING);
        entity.setPublishedOn(entity.getState().equals(StateEnum.PUBLISHED) ? Instant.now() : null);
        return entity;
    }

    public EventFullDto fullDtoFromEntity(EventEntity entity,
                                          CategoryDto categoryDto,
                                          Long confirmedRequests,
                                          UserShortDto userDto,
                                          LocationDto locationDto,
                                          Long views) {
        LocalDateTime publishedOn = Optional.ofNullable(entity.getPublishedOn())
                .map(published -> LocalDateTime.ofInstant(published, ZoneOffset.UTC))
                .orElse(null);
        return EventFullDto.builder()
                .id(entity.getId())
                .annotation(entity.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(confirmedRequests)
                .createdOn(LocalDateTime.ofInstant(entity.getCreatedOn(), ZoneOffset.UTC))
                .description(entity.getDescription())
                .eventDate(LocalDateTime.ofInstant(entity.getEventDate(), ZoneOffset.UTC))
                .initiator(userDto)
                .location(locationDto)
                .paid(entity.isPaid())
                .participantLimit(entity.getParticipantLimit())
                .publishedOn(publishedOn)
                .requestModeration(entity.isRequestModeration())
                .title(entity.getTitle())
                .views(views)
                .state(entity.getState())
                .build();
    }

    public EventShortDto shortDtoFromEntity(EventEntity entity,
                                            CategoryDto categoryDto,
                                            Long confirmedRequests,
                                            UserShortDto userDto,
                                            Long views) {
        return EventShortDto.builder()
                .id(entity.getId())
                .annotation(entity.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(confirmedRequests)
                .eventDate(LocalDateTime.ofInstant(entity.getEventDate(), ZoneOffset.UTC))
                .initiator(userDto)
                .paid(entity.isPaid())
                .title(entity.getTitle())
                .views(views)
                .build();
    }

    public List<EventFullDto> dtoFromEntityList(List<EventEntity> eventEntities,
                                                Map<Long, CategoryDto> categoryMap,
                                                Map<Long, Long> confirmedRequestsMap,
                                                Map<Long, UserShortDto> userDtoMap,
                                                Map<Long, LocationDto> locationMap,
                                                Map<Long, Long> viewsMap) {
        return eventEntities.stream()
                .map(eventEntity ->
                        fullDtoFromEntity(eventEntity,
                                categoryMap.get(eventEntity.getId()),
                                confirmedRequestsMap.get(eventEntity.getId()),
                                userDtoMap.get(eventEntity.getId()),
                                locationMap.get(eventEntity.getId()),
                                viewsMap.get(eventEntity.getId())))
                .collect(Collectors.toList());
    }

    public List<EventShortDto> dtoFromEntityList(List<EventEntity> userEvents,
                                                 Map<Long, CategoryDto> categoryDtoMap,
                                                 Map<Long, Long> confirmedRequestsMap,
                                                 Map<Long, UserShortDto> userShortDtoMap,
                                                 Map<Long, Long> viewsMap) {
        return userEvents.stream()
                .map(eventEntity ->
                        shortDtoFromEntity(eventEntity,
                                categoryDtoMap.get(eventEntity.getId()),
                                confirmedRequestsMap.get(eventEntity.getId()),
                                userShortDtoMap.get(eventEntity.getId()),
                                viewsMap.get(eventEntity.getId())))
                .collect(Collectors.toList());
    }


    public EventParameters getParams(String text,
                                            List<Long> users,
                                            List<String> states,
                                            List<Long> categories,
                                            Boolean paid,
                                            String rangeStart,
                                            String rangeEnd,
                                            Boolean onlyAvailable) {
        return EventParameters.builder()
                .text(text)
                .users(users)
                .states(states != null ?
                        states.stream()
                                .map(StateEnum::fromValue)
                                .collect(Collectors.toList()) : null)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart != null ?
                        LocalDateTime.parse(rangeStart, FORMATTER)
                                .toInstant(ZoneOffset.UTC) :
                        Instant.now())
                .rangeEnd(rangeEnd != null ?
                        LocalDateTime.parse(rangeEnd, FORMATTER)
                                .toInstant(ZoneOffset.UTC) :
                        null)
                .onlyAvailable(onlyAvailable != null ? onlyAvailable : false)
                .build();
    }

    public Map<Long, CategoryEntity> getCategoryEntityMap(List<EventEntity> events) {
        return events.stream().collect(Collectors.toMap(EventEntity::getId, EventEntity::getCategory));
    }

    public List<Long> getEventsIds(List<EventEntity> events) {
        return events.stream().map(EventEntity::getId).collect(Collectors.toList());
    }

    public Map<Long, UserEntity> getUserEntityMap(List<EventEntity> events) {
        return events.stream().collect(Collectors.toMap(EventEntity::getId, EventEntity::getInitiator));
    }

    public Map<Long, LocationEntity> getLocationEntityMap(List<EventEntity> events) {
        return events.stream().collect(Collectors.toMap(EventEntity::getId, EventEntity::getLocation));
    }

    public Map<Long, EventShortDto> eventShortDtoMap(List<EventShortDto> eventShorts) {
        return eventShorts.stream()
                .collect(Collectors.toMap(
                        EventShortDto::getId,
                        Function.identity()));
    }
}
