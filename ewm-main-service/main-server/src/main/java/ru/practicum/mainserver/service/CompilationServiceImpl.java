package ru.practicum.mainserver.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.api.compilation.CompilationDto;
import ru.practicum.mainserver.api.compilation.CompilationMapper;
import ru.practicum.mainserver.api.exception.NotFoundException;
import ru.practicum.mainserver.repository.CompilationRepository;
import ru.practicum.mainserver.repository.entity.CompilationEntity;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.QCompilationEntity;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationMapper mapper;
    private final CompilationRepository repository;

    @Override
    @Transactional
    public CompilationEntity save(CompilationDto body, List<EventEntity> eventEntities) {
        CompilationEntity compilationEntity = repository.save(
                mapper.entityFromDto(
                        body, eventEntities));
        log.debug("{} - successfully saved", compilationEntity);
        return compilationEntity;
    }

    @Override
    @Transactional
    public CompilationEntity update(Long compId, CompilationDto dto, List<EventEntity> eventEntities) {
        CompilationEntity compilation = get(compId);
        compilation.setEvents(eventEntities);
        compilation.setPinned(dto.getPinned());
        compilation.setTitle(dto.getTitle());
        log.debug("Compilation with id {} successfully updated, new data:{}", compId, compilation);
        return compilation;
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        repository.deleteById(compId);
        log.debug("Compilation with id {} successfully deleted", compId);
    }

    @Override
    public CompilationEntity get(Long compId) {
        CompilationEntity compilation = repository.findById(compId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Failed to find compilation with id: " + compId));
        log.debug("Found compilation with id: {}{}", compId, compilation);
        return compilation;
    }

    @Override
    public List<CompilationEntity> get(Boolean pinned, Integer from, Integer size) {
        BooleanExpression byPinned = (pinned != null) ?
                QCompilationEntity.compilationEntity.pinned.eq(pinned) :
                Expressions.TRUE.isTrue();
        Pageable pageRequest = PageRequest.of(from / size, size);
        List<CompilationEntity> foundCompilations = repository.findAll(byPinned, pageRequest).toList();
        log.debug("Found compilations with parameters: pinned - {}, from - {}, size - {}{}",
                pinned, from, size, foundCompilations);
        return foundCompilations;
    }
}
