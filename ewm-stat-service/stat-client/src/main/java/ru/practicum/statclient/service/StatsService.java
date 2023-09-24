package ru.practicum.statclient.service;

import ru.practicum.statdto.EndpointHit;
import ru.practicum.statdto.ViewStats;

import java.time.Instant;
import java.util.List;

public interface StatsService {
    List<ViewStats> getStats(Instant start, Instant end, List<String> uris, boolean unique);

    void saveHit(EndpointHit hit);
}
