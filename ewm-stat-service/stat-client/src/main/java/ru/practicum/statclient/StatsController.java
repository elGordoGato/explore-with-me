package ru.practicum.statclient;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import ru.practicum.statclient.service.StatsService;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statdto.ViewStats;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
        service.saveHit(hit);
    }
}
