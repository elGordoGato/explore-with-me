package ru.practicum.mainserver.api.event.dto;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.compilation.CompilationDto;
import ru.practicum.mainserver.repository.entity.*;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    public EventEntity entityFromDto(EventFullDto dto, UserEntity initiator, LocationEntity location, CategoryEntity category) {
        return new EventEntity();

    }

    public EventFullDto dtoFromEntity(EventEntity entity) {
        return EventFullDto.builder()
                .id(entity.getId())
                .
                .pinned(entity.isPinned())
                .title(entity.getTitle())
                .build();
    }

    public List<EventFullDto> dtoFromEntityList(List<EventEntity> eventEntities) {
        return eventEntities.stream().map(this::dtoFromEntity).collect(Collectors.toList());
    }

}
