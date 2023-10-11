package ru.practicum.mainserver.api.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.CompilationDto;
import ru.practicum.mainserver.api.dao.dto.EventShortDto;
import ru.practicum.mainserver.api.dao.mapper.CategoryMapper;
import ru.practicum.mainserver.api.dao.mapper.CompilationMapper;
import ru.practicum.mainserver.api.dao.mapper.EventMapper;
import ru.practicum.mainserver.api.dao.mapper.UserMapper;
import ru.practicum.mainserver.repository.entity.CompilationEntity;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.service.CompilationService;
import ru.practicum.mainserver.service.EventService;
import ru.practicum.mainserver.service.RequestService;
import ru.practicum.mainserver.service.StatService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final RequestService requestService;
    private final StatService statService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto saveCompilation(@RequestBody @Valid CompilationDto body) {
        log.debug("Received request from admin to save compilation: {}", body);
        List<EventEntity> eventEntities = eventService.getByIds(
                body.getEvents().stream()
                        .map(EventShortDto::getId)
                        .collect(Collectors.toList()));
        CompilationEntity savedCompilation = compilationService.save(body, eventEntities);
        List<EventShortDto> eventShorts = getEventShorts(savedCompilation.getEvents());
        return compilationMapper.dtoFromEntity(savedCompilation, eventShorts);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable("compId") Long compId,
                                            @RequestBody CompilationDto body) {
        log.debug("Received request from admin to update compilation with id: {}, new compilation: {}",
                compId, body);
        List<EventEntity> eventEntities = body.getEvents().isEmpty() ?
                null :
                eventService.getByIds(
                        body.getEvents().stream()
                                .map(EventShortDto::getId)
                                .collect(Collectors.toList()));
        CompilationEntity updatedCompilation = compilationService.update(compId, body, eventEntities);
        List<EventShortDto> eventShorts = getEventShorts(updatedCompilation.getEvents());
        return compilationMapper.dtoFromEntity(updatedCompilation, eventShorts);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compId") Long compId) {
        log.debug("Received request from admin to delete compilation with id: {}", compId);
        compilationService.delete(compId);
    }

    private List<EventShortDto> getEventShorts(List<EventEntity> events) {
        return eventMapper.dtoFromEntityList(
                events,
                categoryMapper.dtoFromEntityMap(
                        eventMapper.getCategoryEntityMap(events)),
                requestService.getConfirmedRequestForEvents(
                        eventMapper.getEventsIds(events)),
                userMapper.dtoFromEntityMap(
                        eventMapper.getUserEntityMap(events)),
                statService.getMap(
                        eventMapper.getEventsIds(events)));
    }
}
