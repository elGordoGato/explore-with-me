package ru.practicum.mainserver.api.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.event.dto.EventFullDto;
import ru.practicum.mainserver.api.event.dto.EventMapper;
import ru.practicum.mainserver.api.event.dto.UpdateEventAdminRequest;
import ru.practicum.mainserver.api.event.utils.EventParameters;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(value = "users", required = false) List<Long> users,
                                        @RequestParam(value = "states", required = false) List<String> states,
                                        @RequestParam(value = "categories", required = false) List<Long> categories,
                                        @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        EventParameters parameters = EventParameters.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .build();
        log.debug("Received request from admin to get events with parameters: {}", parameters);
        List<EventEntity> foundEvents = eventService.getByAdmin(parameters, from, size);
        return eventMapper.dtoFromEntityList(foundEvents);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable("eventId") Long eventId,
                                    @RequestBody @Valid UpdateEventAdminRequest body) {
        log.debug("Received request from admin to update event with id: {},/n new data: {}",
                eventId, body);
        return eventService.updateByAdmin(eventId, body);
    }
}
