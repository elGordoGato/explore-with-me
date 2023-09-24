package ru.practicum.statserver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statdto.ViewStats;
import ru.practicum.statserver.EntityEndpointHit;

import java.time.Instant;
import java.util.List;


@Service
public interface StatsService {
    List<ViewStats> getStats(Instant start, Instant end, List<String> uris, boolean unique);

    @Transactional
    void saveHit(EndpointHit hit);
}
