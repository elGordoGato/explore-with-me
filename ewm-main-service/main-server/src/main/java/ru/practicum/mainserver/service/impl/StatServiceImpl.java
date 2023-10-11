package ru.practicum.mainserver.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriUtils;
import ru.practicum.mainserver.service.StatService;
import ru.practicum.statclient.StatClient;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statdto.ViewStats;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
            PATTERN, new Locale("ru_RU"));
    private final RestTemplate rest;
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Autowired
    public StatServiceImpl(@Value("${STAT_SERVER_URL}") String serverUrl, RestTemplateBuilder builder) {
        this.rest =
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build();
    }

    @Override
    public void saveHit(String ip, String uri) {
        StatClient statClient = new StatClient(rest);
        EndpointHit hit = EndpointHit.builder()
                .app("ewm-main-service")
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC).format(FORMATTER))
                .build();
        statClient.saveHit(hit);
        log.debug("{} - successfully saved to stats", hit);
    }

    @Override
    public Map<Long, Long> getMap(List<Long> eventsIds) {
        StatClient statClient = new StatClient(rest);
        String startEncoded = UriUtils.encode(LocalDateTime.now().minusYears(10L).format(FORMATTER), "UTF-8");
        String endEncoded = UriUtils.encode(LocalDateTime.now(ZoneOffset.UTC).format(FORMATTER), "UTF-8");
        List<String> uris = eventsIds.stream().map(id -> "/events/" + id).collect(Collectors.toList());
        ResponseEntity<Object> statResponse = statClient.getViewStats(startEncoded, endEncoded, uris, true);
        String jsonArray = Objects.requireNonNull(statResponse.getBody()).toString();
        try {
            List<ViewStats> viewStats = objectMapper.readValue(jsonArray, new TypeReference<List<ViewStats>>() {
            });
            log.debug("Found views for ids: {}\n{}", eventsIds, viewStats);
            return viewStats.stream()
                    .collect(Collectors.toMap(
                            stat -> Long.parseLong(
                                    stat.getUri()
                                            .replace("/events/", "")),
                            ViewStats::getHits));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long getViews(Long id) {
        StatClient statClient = new StatClient(rest);
        String startEncoded = UriUtils.encode(LocalDateTime.now().minusYears(10L).format(FORMATTER), "UTF-8");
        String endEncoded = UriUtils.encode(LocalDateTime.now(ZoneOffset.UTC).format(FORMATTER), "UTF-8");
        String uri = "/events/" + id;
        ResponseEntity<Object> statResponse = statClient.getViewStats(startEncoded, endEncoded, List.of(uri), true);
        String jsonArray = Objects.requireNonNull(statResponse.getBody()).toString();
        try {
            List<ViewStats> viewStats = objectMapper.readValue(jsonArray, new TypeReference<List<ViewStats>>() {
            });
            log.debug("Found views for id: {}\n{}", id, viewStats);
            return viewStats.stream()
                    .mapToLong(ViewStats::getHits)
                    .sum();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
