package ru.practicum.mainserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.repository.LocationRepository;
import ru.practicum.mainserver.repository.entity.LocationEntity;
import ru.practicum.mainserver.service.LocationService;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;


    @Override
    public LocationEntity save(LocationEntity locationEntity) {
        return repository.save(locationEntity);
    }
}
