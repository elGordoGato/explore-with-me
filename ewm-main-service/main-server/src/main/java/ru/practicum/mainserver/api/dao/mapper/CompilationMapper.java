package ru.practicum.mainserver.api.dao.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.dao.dto.CompilationDto;
import ru.practicum.mainserver.api.dao.dto.event.EventShortDto;
import ru.practicum.mainserver.repository.entity.CompilationEntity;
import ru.practicum.mainserver.repository.entity.EventEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CompilationMapper {
    public CompilationEntity entityFromDto(CompilationDto dto, List<EventEntity> events) {
        return new CompilationEntity(null, events,
                Optional.ofNullable(dto.getPinned()).orElse(false),
                dto.getTitle());
    }

    public CompilationDto dtoFromEntity(CompilationEntity entity, List<EventShortDto> eventShorts) {
        return CompilationDto.builder()
                .id(entity.getId())
                .events(eventShorts)
                .pinned(entity.isPinned())
                .title(entity.getTitle())
                .build();
    }

    public List<CompilationDto> dtoFromEntityList(List<CompilationEntity> compilationEntities,
                                                  Map<Long, EventShortDto> eventShortDtoMap) {
        return compilationEntities.stream()
                .map(compilation ->
                        getCompilationDto(eventShortDtoMap, compilation))
                .collect(Collectors.toList());
    }

    private CompilationDto getCompilationDto(Map<Long, EventShortDto> eventShortDtoMap,
                                             CompilationEntity compilation) {
        List<EventShortDto> eventShortsForCompilation = compilation
                .getEvents().stream()
                .map(event ->
                        eventShortDtoMap.get(event.getId()))
                .collect(Collectors.toList());

        return dtoFromEntity(compilation, eventShortsForCompilation);
    }

    public List<EventEntity> getAllEvents(List<CompilationEntity> compilations) {
        return compilations.stream()
                .flatMap(compilation -> compilation.getEvents()
                        .stream())
                .collect(Collectors.toList());
    }
}
