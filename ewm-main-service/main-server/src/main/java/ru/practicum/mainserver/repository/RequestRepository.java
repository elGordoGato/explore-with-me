package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.repository.entity.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
}
