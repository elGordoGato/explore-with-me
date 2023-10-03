package ru.practicum.mainserver.api.compilation;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.repository.entity.CompilationEntity;
import ru.practicum.mainserver.repository.entity.EventEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompilationMapper {
    public CompilationEntity entityFromDto(CompilationDto dto, List<EventEntity> events) {
        return new CompilationEntity(null, events, dto.getPinned(), dto.getTitle());
    }

    public CompilationDto dtoFromEntity(CompilationEntity entity) {
        return CompilationDto.builder()
                .id(entity.getId())
                .events(new ArrayList<>()) //TODO events mapper
                .pinned(entity.isPinned())
                .title(entity.getTitle())
                .build();
    }

    public List<CompilationDto> dtoFromEntityList(List<CompilationEntity> compilationEntities) {
        return compilationEntities.stream().map(this::dtoFromEntity).collect(Collectors.toList());
    }

}
