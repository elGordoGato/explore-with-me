package ru.practicum.statserver.repository;

import ru.practicum.statdto.ViewStats;

import java.time.Instant;
import java.util.List;

public interface StatsRepositoryCustom {

    List<ViewStats> findAllHitsForApp(Instant start, Instant end, List<String> uris, boolean unique);
}
