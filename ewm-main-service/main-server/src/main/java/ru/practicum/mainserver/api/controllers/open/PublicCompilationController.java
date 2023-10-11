package ru.practicum.mainserver.api.controllers.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.practicum.mainserver.service.RequestService;
import ru.practicum.mainserver.service.StatService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;
    private final EventMapper eventMapper;
    private final CompilationMapper compilationMapper;
    private final StatService statService;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final RequestService requestService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                @RequestParam(defaultValue = "10") @Positive int size) {
        Supplier<String> pinnedMessage = (() ->
                (pinned != null) ?
                        (pinned ? "" : "not ") +
                                "pinned " :
                        "");
        log.debug("Received public request to get {}compilations from #{} and size: {}",
                pinnedMessage.get(), from, size);

        List<CompilationEntity> compilations = compilationService.get(pinned, from, size);

        List<EventEntity> events = compilationMapper.getAllEvents(compilations);
        List<EventShortDto> eventShorts = getEventShorts(events);
        Map<Long, EventShortDto> eventShortDtoMap = eventMapper.eventShortDtoMap(eventShorts);

        return compilationMapper.dtoFromEntityList(compilations, eventShortDtoMap);

    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable("compId") Long compId) {
        log.debug("Received public request to get compilation with id: {}",
                compId);
        CompilationEntity compilation = compilationService.get(compId);
        List<EventShortDto> eventShorts = getEventShorts(compilation.getEvents());
        return compilationMapper.dtoFromEntity(compilation, eventShorts);
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
