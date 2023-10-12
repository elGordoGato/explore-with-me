package ru.practicum.mainserver.api.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.CompilationDto;
import ru.practicum.mainserver.api.dao.dto.EventShortDto;
import ru.practicum.mainserver.api.dao.mapper.CompilationMapper;
import ru.practicum.mainserver.api.utils.EventFiller;
import ru.practicum.mainserver.api.utils.validation.Marker;
import ru.practicum.mainserver.repository.entity.CompilationEntity;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.service.CompilationService;
import ru.practicum.mainserver.service.EventService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;
    private final EventService eventService;
    private final EventFiller eventFiller;


    @PostMapping
    @Validated(Marker.OnCreate.class)
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto saveCompilation(@RequestBody @Valid CompilationDto body) {
        log.debug("Received request from admin to save compilation: {}", body);
        List<EventEntity> eventEntities = eventService.getByIds(
                Optional.ofNullable(body.getEvents())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(EventShortDto::getId)
                        .collect(Collectors.toList()));
        CompilationEntity savedCompilation = compilationService.save(body, eventEntities);
        List<EventShortDto> eventShorts = eventFiller.getEventShorts(
                savedCompilation.getEvents(), null);
        return compilationMapper.dtoFromEntity(savedCompilation, eventShorts);
    }

    @PatchMapping("/{compId}")
    @Validated(Marker.OnUpdate.class)
    public CompilationDto updateCompilation(@PathVariable("compId") Long compId,
                                            @RequestBody @Valid CompilationDto body) {
        log.debug("Received request from admin to update compilation with id: {}, new compilation: {}",
                compId, body);
        List<EventEntity> eventEntities = Optional.ofNullable(body.getEvents())
                .map(events -> events.stream()
                        .map(EventShortDto::getId)
                        .collect(Collectors.toList()))
                .map(eventService::getByIds)
                .orElse(null);
        CompilationEntity updatedCompilation = compilationService.update(compId, body, eventEntities);
        List<EventShortDto> eventShorts = eventFiller.getEventShorts(
                updatedCompilation.getEvents(), null);
        return compilationMapper.dtoFromEntity(updatedCompilation, eventShorts);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compId") Long compId) {
        log.debug("Received request from admin to delete compilation with id: {}", compId);
        compilationService.delete(compId);
    }
}
