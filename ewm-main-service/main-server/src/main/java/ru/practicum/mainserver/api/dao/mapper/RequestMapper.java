package ru.practicum.mainserver.api.dao.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.dao.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainserver.api.dao.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainserver.api.dao.dto.ParticipationRequestDto;
import ru.practicum.mainserver.api.utils.StatusEnum;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.RequestEntity;
import ru.practicum.mainserver.repository.entity.UserEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RequestMapper {
    public RequestEntity entityFromDto(UserEntity requester, EventEntity event) {
        RequestEntity request = new RequestEntity();
        request.setRequester(requester);
        request.setEvent(event);
        request.setStatus(event.getParticipantLimit() == 0 || !event.isRequestModeration() ?
                StatusEnum.CONFIRMED : StatusEnum.PENDING);
        return request;
    }

    public ParticipationRequestDto dtoFromEntity(RequestEntity entity) {
        return ParticipationRequestDto.builder()
                .id(entity.getId())
                .requester(entity.getRequester().getId())
                .event(entity.getEvent().getId())
                .created(LocalDateTime.ofInstant(entity.getCreated(), ZoneOffset.UTC))
                .status(entity.getStatus())
                .build();
    }

    public List<ParticipationRequestDto> dtoFromEntityList(List<RequestEntity> categoryEntities) {
        return categoryEntities.stream().map(this::dtoFromEntity).collect(Collectors.toList());
    }

    public Map<Long, ParticipationRequestDto> dtoFromEntityMap(Map<Long, RequestEntity> requestEntityMap) {
        return requestEntityMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> dtoFromEntity(
                                e.getValue())));
    }

    public EventRequestStatusUpdateResult getStatusUpdateResult(Map<String, List<RequestEntity>> updateResult) {
        return new EventRequestStatusUpdateResult(
                dtoFromEntityList(updateResult.get("confirmedRequests")),
                dtoFromEntityList(updateResult.get("rejectedRequests")));
    }
}
