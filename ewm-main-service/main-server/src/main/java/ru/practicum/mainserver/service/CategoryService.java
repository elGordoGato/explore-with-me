package ru.practicum.mainserver.service;

import ru.practicum.mainserver.api.dao.dto.CategoryDto;
import ru.practicum.mainserver.repository.entity.CategoryEntity;

import java.util.List;
import java.util.Map;


public interface CategoryService {
    CategoryEntity add(CategoryDto body);

    CategoryEntity update(Long catId, CategoryDto body);

    void delete(Long catId);

    List<CategoryEntity> get(Integer from, Integer size);

    CategoryEntity get(Long catId);

    Map<Long, CategoryEntity> getMapForEvents(List<Long> eventIds);

    CategoryEntity check(Long category);
}
