package ru.practicum.statserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statdto.ViewStats;

import java.time.Instant;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    @Query(value = "SELECT new ru.practicum.statdto.ViewStats(" +
            "h.app, h.uri, COUNT(DISTINCT(h.ip)))" +
            "FROM EntityEndpointHit h " +
            "WHERE h.timestamp >= ?1 " +
            "AND h.timestamp <= ?2 " +
            "AND h.uri IN(?3) " +
            "GROUP BY h.app, h.uri")
    List<ViewStats> findAllWithUniqueIp(Instant start, Instant end, List<String> uris);

    @Query(value = "SELECT new ru.practicum.statdto.ViewStats(" +
            "h.app, h.uri, COUNT(h))" +
            "FROM EntityEndpointHit h " +
            "WHERE h.timestamp >= ?1 " +
            "AND h.timestamp <= ?2 " +
            "AND h.uri IN(?3) " +
            "GROUP BY h.app, h.uri")
    List<ViewStats> findAllWithSameIp(Instant start, Instant end, List<String> uris);
}
