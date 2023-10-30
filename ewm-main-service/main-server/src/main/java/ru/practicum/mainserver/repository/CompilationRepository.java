package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.repository.entity.CompilationEntity;

import java.util.Optional;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long> {

    @EntityGraph(value = "with-events")
    Optional<CompilationEntity> findWithEventsById(Long compId);
}
