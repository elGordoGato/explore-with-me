package ru.practicum.mainserver.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.api.dao.dto.CompilationDto;
import ru.practicum.mainserver.api.dao.mapper.CompilationMapper;
import ru.practicum.mainserver.api.utils.exception.NotFoundException;
import ru.practicum.mainserver.repository.CompilationRepository;
import ru.practicum.mainserver.repository.entity.CompilationEntity;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.QCompilationEntity;
import ru.practicum.mainserver.service.CompilationService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

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
        Optional.ofNullable(dto.getTitle())
                .ifPresent(title -> setTitle(compilation, title));
        Optional.ofNullable(dto.getPinned())
                .ifPresent(compilation::setPinned);
        Optional.ofNullable(eventEntities)
                .ifPresent(compilation::setEvents);
        log.debug("Compilation with id {} successfully updated, new data:{}", compId, compilation);
        return compilation;
    }

    private void setTitle(CompilationEntity compilation, @NotBlank String title) {
        compilation.setTitle(title);
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
                        new NotFoundException(CompilationEntity.class, compId));
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
