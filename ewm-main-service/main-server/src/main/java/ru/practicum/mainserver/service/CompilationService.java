package ru.practicum.mainserver.service;

import ru.practicum.mainserver.api.compilation.CompilationDto;
import ru.practicum.mainserver.repository.entity.CompilationEntity;
import ru.practicum.mainserver.repository.entity.EventEntity;

import java.util.List;


public interface CompilationService {

    CompilationEntity save(CompilationDto body, List<EventEntity> eventEntities);

    CompilationEntity update(Long compId, CompilationDto body, List<EventEntity> eventEntities);

    void delete(Long compId);

    CompilationEntity get(Long compId);

    List<CompilationEntity> get(Boolean pinned, Integer from, Integer size);
}
