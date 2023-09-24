package ru.practicum.statserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import ru.practicum.statdto.ViewStats;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statserver.service.StatsService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN, new Locale("ru_RU"));
    private static final ZoneId UTC = ZoneId.of("UTC");

    private final StatsService service;

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam List<String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        log.debug("Received request to get stats from {} to {} for uris: {} for " +
                        (unique ? "" : "not") + " unique ip",
                start, end, uris);
        Instant startRange = LocalDateTime.parse(
                        UriUtils.decode(start, "UTF-8"),
                        FORMATTER)
                .atZone(UTC)
                .toInstant();
        Instant endRange = LocalDateTime.parse(
                        UriUtils.decode(end, "UTF-8"),
                        FORMATTER)
                .atZone(UTC)
                .toInstant();
        return service.getStats(startRange, endRange, uris, unique);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public void saveHit(@RequestBody EndpointHit hit) {
        log.debug("Received request to save {} to stats", hit);
        //EntityEndpointHit entityEndpointHit = new EntityEndpointHit(hit);
        service.saveHit(hit);
    }
}
