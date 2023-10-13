package ru.practicum.statclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statdto.ViewStats;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@Service
public class StatClient {
    private final RestTemplate rest;

    @Autowired
    public StatClient(@Value("${STAT_SERVER_URL}") String serverUrl, RestTemplateBuilder builder) {
        this.rest =
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build();
    }

    public void saveHit(EndpointHit hitDto) {
        HttpEntity<EndpointHit> requestEntity = new HttpEntity<>(hitDto, defaultHeaders());
        rest.postForLocation("/hit", requestEntity, String.class);
    }

    public List<ViewStats> getViewStats(String start, String end, String uris, boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, defaultHeaders());
        ResponseEntity<List<ViewStats>> response = rest
                .exchange("/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                        GET,
                        requestEntity,
                        new ParameterizedTypeReference<List<ViewStats>>() {
                        },
                        parameters);
        return response.getBody();
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setAccept(List.of(APPLICATION_JSON));
        return headers;
    }
}
