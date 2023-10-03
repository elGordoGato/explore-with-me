package ru.practicum.mainserver.api.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.event.dto.*;
import ru.practicum.mainserver.api.request.ParticipationRequestDto;
import ru.practicum.mainserver.service.EventService;
import ru.practicum.mainserver.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    EventService eventService;
    RequestService requestService;

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable("userId") Long userId,
                                         @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        log.debug("Received request to get all user's events for user with id: {};/n" +
                        "Page: from - {}, size - {}",
                userId, from, size);
        return eventService.getByUser(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable("userId") Long userId,
                                 @RequestBody @Valid NewEventDto body) {
        log.debug("Received request from user with id: {} to post event: {}",
                userId, body);
        return eventService.addByUser(userId, body);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable("userId") Long userId,
                                 @PathVariable("eventId") Long eventId) {
        log.debug("Received request from user with id: {} to get event with id: {}",
                userId, eventId);
        return eventService.getByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventRequestStatusUpdateResult changeRequestStatus(@PathVariable("userId") Long userId,
                                                              @PathVariable("eventId") Long eventId,
                                                              @RequestBody @Valid EventRequestStatusUpdateRequest body) {
        log.debug("Received request from user with id: {} to update event with id: {}, new data: {}",
                userId, eventId, body);
        return eventService.updateByUser(userId, eventId, body);
    }


    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventParticipiationRequests(@PathVariable("userId") Long userId,
                                                                        @PathVariable("eventId") Long eventId) {
        log.debug("Received request from user with id: {} to get participation requests for event with id: {}",
                userId, eventId);
        return requestService.getByEvent(userId, eventId);
    }


    @PatchMapping("/{eventId}/requests")
    public EventFullDto updateRequestsForEvent(@PathVariable("userId") Long userId,
                                               @PathVariable("eventId") Long eventId,
                                               @RequestBody @Valid UpdateEventUserRequest body) {
        log.debug("Received request from user with id: {} to get update participation request for event with id: {}, new data: {}",
                userId, eventId, body);
        return requestService.updateForEvent(userId, eventId, body);
    }

}
