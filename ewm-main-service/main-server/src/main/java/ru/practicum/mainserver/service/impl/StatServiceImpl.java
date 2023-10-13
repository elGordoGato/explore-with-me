package ru.practicum.mainserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;
import ru.practicum.mainserver.service.StatService;
import ru.practicum.statclient.StatClient;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statdto.ViewStats;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
            PATTERN, new Locale("ru_RU"));
    private final StatClient statClient;

    @Override
    public void saveHit(String ip, String uri) {
        EndpointHit hit = EndpointHit.builder()
                .app("ewm-main-service")
                .uri(uri)
                .ip(ip)
                .timestamp(now(UTC).format(FORMATTER))
                .build();
        statClient.saveHit(hit);
        log.debug("{} - successfully saved to stats", hit);
    }

    @Override
    public Map<Long, Long> getMap(List<Long> eventsIds) {
        String startEncoded = UriUtils.encode(
                now().minusYears(10)
                        .format(FORMATTER), "UTF-8");
        String endEncoded = UriUtils.encode(
                now(UTC)
                        .format(FORMATTER), "UTF-8");
        String uris = eventsIds
                .stream()
                .map(id -> "/events/" + id)
                .collect(Collectors.joining(", "));
        List<ViewStats> viewStats = statClient.getViewStats(startEncoded, endEncoded, uris, true);
        log.debug("Found views for ids: {}\n{}", eventsIds, viewStats);
        return viewStats.stream()
                .collect(Collectors.toMap(
                        stat -> Long.parseLong(
                                stat.getUri()
                                        .replace("/events/", "")),
                        ViewStats::getHits));
    }

    @Override
    public Long getViews(Long id) {
        String startEncoded = UriUtils.encode(
                now().minusYears(10)
                        .format(FORMATTER), "UTF-8");
        String endEncoded = UriUtils.encode(
                now(UTC)
                        .format(FORMATTER), "UTF-8");
        String uri = "/events/" + id;
        List<ViewStats> viewStats = statClient.getViewStats(startEncoded, endEncoded, uri, true);

        log.debug("Found views for id: {}\n{}", id, viewStats);
        return viewStats.stream()
                .mapToLong(ViewStats::getHits)
                .sum();
    }
}
