package ru.practicum.mainserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.api.dao.dto.CategoryDto;
import ru.practicum.mainserver.api.dao.mapper.CategoryMapper;
import ru.practicum.mainserver.api.utils.exception.NotFoundException;
import ru.practicum.mainserver.repository.CategoryRepository;
import ru.practicum.mainserver.repository.entity.CategoryEntity;
import ru.practicum.mainserver.service.CategoryService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper mapper;
    private final CategoryRepository repository;

    @Override
    @Transactional
    public CategoryEntity add(CategoryDto body) {
        CategoryEntity category = repository.save(
                mapper.entityFromDto(body));
        log.debug("{} was added to db", category);
        return category;
    }

    @Override
    @Transactional
    public CategoryEntity update(Long catId, CategoryDto body) {
        CategoryEntity category = get(catId);
        category.setName(body.getName());
        log.debug("Category with id {} was updated, new data: {}", catId, category);
        return category;
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        repository.deleteById(catId);
        log.debug("Category with id {} was deleted", catId);

    }

    @Override
    public List<CategoryEntity> get(Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of(from / size, size);
        List<CategoryEntity> foundCategories = repository.findAll(pageRequest).toList();
        log.debug("Found categories page: from {}, size {}. Content: {}", from, size, foundCategories);
        return foundCategories;
    }

    @Override
    public CategoryEntity get(Long catId) {
        CategoryEntity category = repository.findById(catId).orElseThrow(() ->
                new NotFoundException(CategoryEntity.class, catId));
        log.debug("Found category: {}", category);
        return category;
    }
}
