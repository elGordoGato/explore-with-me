package ru.practicum.statserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statdto.ViewStats;
import ru.practicum.statserver.api.BadRequestException;
import ru.practicum.statserver.repository.HitEntity;
import ru.practicum.statserver.repository.StatsRepository;

import java.time.Instant;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    @Override
    public List<ViewStats> getStats(Instant start, Instant end, List<String> uris, boolean unique) {
        if(start.isAfter(end)){
            throw new BadRequestException("End date should be after start date");
        }
        List<ViewStats> foundStats = repository.findAllHitsForApp(start, end, uris, unique);
        log.debug("found stats: {}", foundStats);
        return foundStats;
    }

    @Override
    public void saveHit(HitEntity hit) {
        repository.save(hit);
        log.debug("Hit {} successfully saved to stats", hit);
    }
}
