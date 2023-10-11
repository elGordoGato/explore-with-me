package ru.practicum.mainserver.service;

import ru.practicum.mainserver.repository.entity.LocationEntity;

public interface LocationService {
    LocationEntity save(LocationEntity locationEntity);
}
