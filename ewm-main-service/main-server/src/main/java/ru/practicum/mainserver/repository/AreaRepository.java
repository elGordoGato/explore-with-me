package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.repository.entity.AreaEntity;

import java.util.List;
import java.util.Optional;

public interface AreaRepository extends JpaRepository<AreaEntity, Long> {

    @EntityGraph(value = "with-location")
    List<AreaEntity> getAllWithLocationBy();

    @EntityGraph(value = "with-location")
    Optional<AreaEntity> findOneWithLocationById(Long areaId);
}
