package ru.practicum.mainserver.api.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.service.CompilationService;
import ru.practicum.mainserver.service.EventService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    CompilationService compilationService;
    EventService eventService;
    CompilationMapper compilationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto saveCompilation(@RequestBody @Valid CompilationDto body) {
        log.debug("Received request from admin to save compilation: {}", body);
        List<EventEntity> eventEntities = null;                                                     //TODO event service
        return compilationMapper.dtoFromEntity(compilationService.save(body, eventEntities));
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable("compId") Long compId,
                                            @RequestBody @Valid CompilationDto body) {
        log.debug("Received request from admin to update compilation with id: {}, new compilation: {}",
                compId, body);
        List<EventEntity> eventEntities = null;                                                      //TODO event service
        return compilationMapper.dtoFromEntity(compilationService.update(compId, body, eventEntities));
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compId") Long compId) {
        log.debug("Received request from admin to delete compilation with id: {}", compId);
        compilationService.delete(compId);
    }
}
