package ru.practicum.mainserver.api.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.event.EventFullDto;
import ru.practicum.mainserver.api.dao.dto.event.InputEventDto;
import ru.practicum.mainserver.api.dao.mapper.EventMapper;
import ru.practicum.mainserver.api.dao.mapper.LocationMapper;
import ru.practicum.mainserver.api.utils.EventFiller;
import ru.practicum.mainserver.api.utils.EventParameters;
import ru.practicum.mainserver.api.utils.validation.Marker;
import ru.practicum.mainserver.api.utils.validation.StateByAdmin;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.LocationEntity;
import ru.practicum.mainserver.service.EventService;
import ru.practicum.mainserver.service.LocationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final EventFiller eventFiller;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(value = "users", required = false) List<Long> users,
                                        @RequestParam(value = "states", required = false) List<String> states,
                                        @RequestParam(value = "categories", required = false) List<Long> categories,
                                        @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        EventParameters parameters = eventMapper.getParams(null,
                users, states, categories, null, rangeStart, rangeEnd, null);
        log.debug("Received request from admin to get events with parameters: {}", parameters);
        List<EventEntity> foundEvents = eventService.getFullByParamsSortedById(parameters, from, size);
        return eventFiller.getFullDtoList(foundEvents);
    }

    @PatchMapping("/{eventId}")
    @Validated({Marker.OnUpdate.class, StateByAdmin.class})
    public EventFullDto updateEvent(@PathVariable("eventId") Long eventId, @RequestBody @Valid InputEventDto body) {
        log.debug("Received request from admin to update event with id: {},\n new data: {}", eventId, body);
        LocationEntity newLocation = (body.getLocationDto() != null)
                ? locationService.save(locationMapper.entityFromDto(body.getLocationDto()))
                : null;
        EventEntity updatedEvent = eventService.updateByAdmin(eventId, body, newLocation);
        return eventFiller.getEventFullDto(updatedEvent);
    }
}
