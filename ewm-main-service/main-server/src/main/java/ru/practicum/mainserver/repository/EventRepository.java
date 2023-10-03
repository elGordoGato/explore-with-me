package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.repository.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
