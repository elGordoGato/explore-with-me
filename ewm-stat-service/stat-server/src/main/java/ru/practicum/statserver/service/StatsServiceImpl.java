package ru.practicum.statserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.practicum.statdto.ViewStats;
import ru.practicum.statserver.HitEntity;
import ru.practicum.statserver.StatsRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<ViewStats> getStats(Instant start, Instant end, List<String> uris, boolean unique) {
        if (uris == null){
            uris = new ArrayList<>();
        }
        List<ViewStats> foundStats = unique ?
                repository.findAllWithUniqueIp(start, end, uris) :
                repository.findAllWithSameIp(start, end, uris);
        log.debug("found stats: {}", foundStats);
        return foundStats;
    }

    @Transactional
    @Override
    public void saveHit(HitEntity hit) {
        repository.save(hit);
        log.debug("Hit {} successfully saved to stats", hit);

    }
}
