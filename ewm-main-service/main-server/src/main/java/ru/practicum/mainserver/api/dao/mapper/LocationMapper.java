package ru.practicum.mainserver.api.dao.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.dao.dto.LocationDto;
import ru.practicum.mainserver.repository.EventRepository;
import ru.practicum.mainserver.repository.LocationRepository;
import ru.practicum.mainserver.repository.entity.LocationEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public LocationMapper(EventRepository eventRepository,
                          LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    public LocationEntity entityFromDto(LocationDto dto) {
        return new LocationEntity(null, dto.getLat(), dto.getLon());
    }

    public LocationDto dtoFromEntity(LocationEntity location) {
        return LocationDto.builder()
                .id(location.getId())
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public Map<Long, LocationDto> dtoFromEntityMap(Map<Long, LocationEntity> locationMap) {
        return locationMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> dtoFromEntity(
                                e.getValue())));
    }

    public Map<Long, LocationDto> dtoMapFromEntityList(List<LocationEntity> locations) {
        return Set.copyOf(locations).stream()
                .collect(Collectors.toMap(LocationEntity::getId,
                        this::dtoFromEntity));
    }
}
