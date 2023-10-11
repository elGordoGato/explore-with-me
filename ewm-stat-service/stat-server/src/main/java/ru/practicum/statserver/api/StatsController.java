package ru.practicum.statserver.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statdto.ViewStats;
import ru.practicum.statserver.repository.HitEntity;
import ru.practicum.statserver.service.StatsService;

import java.time.Instant;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {


    private final StatsService service;

    private final HitMapper mapper;

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        log.debug("Received request to get stats from {} to {} for uris: {} for " +
                        (unique ? "" : "not") + " unique ip",
                UriUtils.decode(start, "UTF-8"), UriUtils.decode(end, "UTF-8"), uris);
        Instant startRange = mapper.getInstant(start);
        Instant endRange = mapper.getInstant(end);
        return service.getStats(startRange, endRange, uris, unique);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public void saveHit(@RequestBody EndpointHit hitDto) {
        log.debug("Received request to save {} to stats", hitDto);
        HitEntity hit = mapper.makeHitEntity(hitDto);
        service.saveHit(hit);
    }
}
