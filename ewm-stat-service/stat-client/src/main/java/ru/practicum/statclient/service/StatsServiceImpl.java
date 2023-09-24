package ru.practicum.statclient.service;

import org.springframework.stereotype.Service;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statdto.ViewStats;

import java.time.Instant;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {
    @Override
    public List<ViewStats> getStats(Instant start, Instant end, List<String> uris, boolean unique) {
        return null;
    }

    @Override
    public void saveHit(EndpointHit hit) {

    }
}
