package ru.practicum.mainserver.api.category;

import org.springframework.stereotype.Component;
import ru.practicum.mainserver.repository.entity.CategoryEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
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
        return categoryEntities.stream().map(this::dtoFromEntity).collect(Collectors.toList());
    }
}
