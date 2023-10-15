package ru.practicum.mainserver.service;

import ru.practicum.mainserver.api.dao.dto.AreaDto;
import ru.practicum.mainserver.repository.entity.AreaEntity;
import ru.practicum.mainserver.repository.entity.LocationEntity;

import java.util.List;

public interface AreaService {
    AreaEntity addNew(AreaEntity area);

    List<AreaEntity> findAll();

    void delete(Long areaId);

    AreaEntity getById(Long areaId);

    AreaEntity update(Long areaId, AreaDto body, LocationEntity location);
}
