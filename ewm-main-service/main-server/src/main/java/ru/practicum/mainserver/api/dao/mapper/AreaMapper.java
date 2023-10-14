package ru.practicum.mainserver.api.dao.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.api.dao.dto.CategoryDto;
import ru.practicum.mainserver.repository.entity.CategoryEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AreaMapper {
    public CategoryEntity entityFromDto(CategoryDto dto) {
        return new CategoryEntity(null, dto.getName());
    }

    public CategoryDto dtoFromEntity(CategoryEntity entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public List<CategoryDto> dtoFromEntityList(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::dtoFromEntity)
                .collect(Collectors.toList());
    }

    public Map<Long, CategoryDto> dtoFromEntityMap(Map<Long, CategoryEntity> categoryEntityMap) {
        return categoryEntityMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> dtoFromEntity(
                                e.getValue())));
    }
}
