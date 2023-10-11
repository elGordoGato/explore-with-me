package ru.practicum.mainserver.api.dao.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.dao.dto.LocationDto;
import ru.practicum.mainserver.repository.entity.LocationEntity;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LocationMapper {
    public LocationEntity entityFromDto(LocationDto dto) {
        return new LocationEntity(null, dto.getLat(), dto.getLon());
    }

    public LocationDto dtoFromEntity(LocationEntity location) {
        return LocationDto.builder()
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
}
