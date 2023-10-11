package ru.practicum.mainserver.api.controllers.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.ParticipationRequestDto;
import ru.practicum.mainserver.api.dao.mapper.RequestMapper;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.RequestEntity;
import ru.practicum.mainserver.repository.entity.UserEntity;
import ru.practicum.mainserver.service.EventService;
import ru.practicum.mainserver.service.RequestService;
import ru.practicum.mainserver.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {
    private final UserService userService;
    private final EventService eventService;
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        log.debug("Received request to get participation requests of user with id: {}",
                userId);
        List<RequestEntity> foundRequests = requestService.getUserRequests(userId);
        return requestMapper.dtoFromEntityList(foundRequests);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addParticipationRequest(@PathVariable Long userId,
                                                           @RequestParam @Valid Long eventId) {
        log.debug("Received request to create participation request in event with id: {} from user with id: {}",
                eventId, userId);
        UserEntity requester = userService.getById(userId);
        EventEntity event = eventService.getPublic(eventId);
        RequestEntity newRequest = requestMapper.entityFromDto(requester, event);
        RequestEntity addedRequest = requestService.addParticipationRequest(newRequest);
        return requestMapper.dtoFromEntity(addedRequest);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        log.debug("Received request to cancel participation request with id: {} from user with id: {}",
                requestId, userId);
        RequestEntity canceledRequest = requestService.cancelRequest(userId, requestId);
        return requestMapper.dtoFromEntity(canceledRequest);
    }
}
