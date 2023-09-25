package ru.practicum.statserver.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statdto.ViewStats;
import ru.practicum.statserver.HitEntity;

import java.time.Instant;
import java.util.List;



public interface StatsService {
    List<ViewStats> getStats(Instant start, Instant end, List<String> uris, boolean unique);

    @Transactional
    void saveHit(HitEntity hit);
}
