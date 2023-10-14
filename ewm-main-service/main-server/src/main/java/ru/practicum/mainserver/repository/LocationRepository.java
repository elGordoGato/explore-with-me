package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.repository.entity.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
}
