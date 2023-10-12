package ru.practicum.mainserver.api.controllers.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.EventFullDto;
import ru.practicum.mainserver.api.dao.dto.EventShortDto;
import ru.practicum.mainserver.api.dao.mapper.EventMapper;
import ru.practicum.mainserver.api.utils.EventFiller;
import ru.practicum.mainserver.api.utils.EventParameters;
import ru.practicum.mainserver.api.utils.SortEnum;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.service.EventService;
import ru.practicum.mainserver.service.StatService;

import javax.servlet.http.HttpServletRequest;
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
    private final StatService statService;
    private final EventFiller eventFiller;


    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable("id") Long id,
                                 HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        log.info("client ip: {}", ip);
        log.info("endpoint path: {}", uri);
        log.debug("Received public request to get event with id: {}", id);
        EventEntity event = eventService.getPublic(id);
        EventFullDto fullDto = eventFiller.getEventFullDto(event);
        statService.saveHit(ip, uri);
        return fullDto;
    }


    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(value = "text", required = false)
                                         @Size(min = 1, max = 7000) String text,
                                         @RequestParam(value = "categories", required = false)
                                         List<Long> categories,
                                         @RequestParam(value = "paid", required = false)
                                         Boolean paid,
                                         @RequestParam(value = "rangeStart", required = false)
                                         String rangeStart,
                                         @RequestParam(value = "rangeEnd", required = false)
                                         String rangeEnd,
                                         @RequestParam(value = "onlyAvailable", defaultValue = "false")
                                         boolean onlyAvailable,
                                         @RequestParam(value = "sort", defaultValue = "EVENT_DATE")
                                         SortEnum sort,
                                         @RequestParam(value = "from", defaultValue = "0")
                                         @PositiveOrZero Integer from,
                                         @RequestParam(value = "size", defaultValue = "10")
                                         @Positive Integer size,
                                         HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        log.debug("client ip: {} | endpoint path: {}", ip, uri);
        EventParameters eventParameters = eventMapper.getParams(
                text, null, null, categories, paid, rangeStart, rangeEnd, onlyAvailable);
        log.info("Received public request to get events with parameters: {}", eventParameters);
        List<EventEntity> foundSortedEvents;
        Map<Long, Long> viewsMap = null;
        if (sort.equals(SortEnum.VIEWS)) {
            List<Long> foundEventIds = eventService.getIdsByParams(eventParameters);
            viewsMap = statService.getMap(foundEventIds);
            foundSortedEvents = eventService.getShortSortedByViews(
                    viewsMap, from, size);
        } else {
            foundSortedEvents = eventService.getShortByParamsSortedByEventDate(
                    eventParameters, from, size);
        }
        List<EventShortDto> shortDtoList = eventFiller.getEventShorts(foundSortedEvents, viewsMap);
        statService.saveHit(ip, uri);
        return shortDtoList;
    }

}
