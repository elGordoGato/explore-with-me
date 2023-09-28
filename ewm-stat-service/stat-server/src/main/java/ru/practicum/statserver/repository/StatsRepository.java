package ru.practicum.statserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatsRepository extends JpaRepository<HitEntity, Long>, StatsRepositoryCustom {
}
