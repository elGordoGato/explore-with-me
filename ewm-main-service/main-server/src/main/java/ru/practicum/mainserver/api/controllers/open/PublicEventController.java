package ru.practicum.mainserver.api.controllers.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.*;
import ru.practicum.mainserver.api.dao.mapper.CategoryMapper;
import ru.practicum.mainserver.api.dao.mapper.EventMapper;
import ru.practicum.mainserver.api.dao.mapper.LocationMapper;
import ru.practicum.mainserver.api.dao.mapper.UserMapper;
import ru.practicum.mainserver.api.utils.EventParameters;
import ru.practicum.mainserver.api.utils.SortEnum;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final RequestService requestService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final StatService statService;


    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable("id") Long id,
                                 HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        log.info("client ip: {}", ip);
        log.info("endpoint path: {}", uri);
        log.debug("Received public request to get event with id: {}", id);
        EventEntity event = eventService.getPublic(id);
        CategoryDto categoryDto = categoryMapper.dtoFromEntity(event.getCategory());
        Long confirmedRequest = requestService.getConfirmedRequestForEvent(event.getId());
        UserShortDto userShortDto = userMapper.shortDtoFromEntity(event.getInitiator());
        LocationDto locationDto = locationMapper.dtoFromEntity(event.getLocation());
        Long views = statService.getViews(event.getId()); //TODO views
        statService.saveHit(ip, uri);
        return eventMapper.fullDtoFromEntity(
                event,
                categoryDto,
                confirmedRequest,
                userShortDto,
                locationDto,
                views);
    }


    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(value = "text", required = false) @Size(min = 1, max = 7000) @Valid String text,
                                         @RequestParam(value = "categories", required = false) @Valid List<Long> categories,
                                         @RequestParam(value = "paid", required = false) Boolean paid,
                                         @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                         @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                         @RequestParam(value = "onlyAvailable", defaultValue = "false") boolean onlyAvailable,
                                         @RequestParam(value = "sort", defaultValue = "EVENT_DATE") SortEnum sort,
                                         @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(value = "size", defaultValue = "10") @Positive Integer size,
                                         HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        log.info("client ip: {}", ip);
        log.info("endpoint path: {}", uri);
        EventParameters eventParameters = eventMapper.getParams(
                text, null, null, categories, paid, rangeStart, rangeEnd, onlyAvailable);
        log.info("Received public request to get events with parameters: {}", eventParameters);
        List<EventEntity> foundSortedEvents;
        Map<Long, Long> viewsMap;
        if (sort.equals(SortEnum.VIEWS)) {
            List<Long> foundEventIds = eventService.getIdsByParams(eventParameters);
            viewsMap = statService.getMap(foundEventIds);
            foundSortedEvents = eventService.getShortSortedByViews(
                    viewsMap, from, size);
        } else {
            foundSortedEvents = eventService.getShortByParamsSortedByEventDate(
                    eventParameters, from, size);
            viewsMap = statService.getMap(
                    eventMapper.getEventsIds(foundSortedEvents));
        }
        Map<Long, CategoryDto> categoryDtoMap = categoryMapper.dtoFromEntityMap(
                eventMapper.getCategoryEntityMap(foundSortedEvents));
        Map<Long, UserShortDto> userShortDtoMap = userMapper.dtoFromEntityMap(
                eventMapper.getUserEntityMap(foundSortedEvents));
        Map<Long, Long> confirmedRequestsMap = requestService.getConfirmedRequestForEvents(
                eventMapper.getEventsIds(foundSortedEvents));
        statService.saveHit(ip, uri);
        return eventMapper.dtoFromEntityList(foundSortedEvents,
                categoryDtoMap,
                confirmedRequestsMap,
                userShortDtoMap,
                viewsMap);
    }

}
