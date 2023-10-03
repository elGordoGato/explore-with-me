package ru.practicum.mainserver.api.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.event.dto.EventFullDto;
import ru.practicum.mainserver.api.event.dto.EventShortDto;
import ru.practicum.mainserver.api.event.utils.EventParameters;
import ru.practicum.mainserver.api.event.utils.SortEnum;
import ru.practicum.mainserver.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {
    EventService eventService;


    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable("id") Long id) {
        log.debug("Received public request to get event with id: {}", id);
        return eventService.getPublic(id);
    }

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(value = "text", required = false) @Size(min = 1, max = 7000) @Valid String text,
                                         @RequestParam(value = "categories", required = false) @Valid List<Long> categories,
                                         @RequestParam(value = "paid", required = false) Boolean paid,
                                         @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                         @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                         @RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(value = "sort") SortEnum sort,
                                         @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        EventParameters eventParameters = EventParameters.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .build();
        log.info("Received public request to get events with parameters: {}", eventParameters);
        return eventService.getPublic(eventParameters, from, size);
    }

}
