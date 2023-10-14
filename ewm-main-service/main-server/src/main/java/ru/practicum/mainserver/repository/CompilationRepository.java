package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.mainserver.repository.entity.CompilationEntity;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long>,
        QuerydslPredicateExecutor<CompilationEntity> {
}
