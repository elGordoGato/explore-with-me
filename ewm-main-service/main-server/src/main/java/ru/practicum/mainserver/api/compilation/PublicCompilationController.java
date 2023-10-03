package ru.practicum.mainserver.api.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.service.CompilationService;
import ru.practicum.mainserver.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    CompilationService compilationService;
    EventService eventService;
    CompilationMapper compilationMapper;

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
        return compilationMapper.dtoFromEntityList(
                compilationService.get(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable("compId") Long compId) {
        log.debug("Received public request to get compilation with id: {}",
                compId);
        return compilationMapper.dtoFromEntity(
                compilationService.get(compId));
    }
}
