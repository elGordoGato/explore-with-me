package ru.practicum.mainserver.api.dao.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.dao.dto.AreaDto;
import ru.practicum.mainserver.api.dao.dto.LocationDto;
import ru.practicum.mainserver.api.dao.dto.event.EventShortDto;
import ru.practicum.mainserver.repository.entity.AreaEntity;
import ru.practicum.mainserver.repository.entity.LocationEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AreaMapper {
    public AreaEntity entityFromDto(AreaDto dto, LocationEntity location) {
        return new AreaEntity(null, dto.getTitle(), location, dto.getRadius());
    }

    public AreaDto dtoFromEntity(AreaEntity area, LocationDto location, List<EventShortDto> events) {
        return AreaDto.builder()
                .id(area.getId())
                .title(area.getTitle())
                .location(location)
                .radius(area.getRadius())
                .events(events)
                .build();
    }

    public List<AreaDto> dtoFromEntityList(List<AreaEntity> areaEntities,
                                           Map<Long, LocationDto> locationDtoMap) {
        return areaEntities.stream()
                .map(area -> dtoFromEntity(area,
                        locationDtoMap.get(area.getLocation().getId()),
                        null))
                .collect(Collectors.toList());
    }

    public List<LocationEntity> getLocationEntityList(List<AreaEntity> areas) {
        return areas.stream()
                .map(AreaEntity::getLocation)
                .collect(Collectors.toList());
    }
}
