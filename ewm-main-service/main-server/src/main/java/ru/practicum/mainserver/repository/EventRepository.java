package ru.practicum.mainserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.mainserver.api.dao.dto.EventFullDto;
import ru.practicum.mainserver.repository.entity.EventEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long>, QuerydslPredicateExecutor<EventFullDto>, EventRepositoryCustom {
    List<EventEntity> findByIdIn(Collection<Long> ids);

    @EntityGraph(value = "short-event")
    Optional<EventEntity> findShortByIdAndInitiatorId(Long eventId, Long userId);

    @EntityGraph(value = "short-event")
    List<EventEntity> findShortByInitiatorId(Long userId, Pageable pageRequest);

    @EntityGraph(value = "short-event")
    List<EventEntity> findShortByIdIn(List<Long> sortedIds);
}
