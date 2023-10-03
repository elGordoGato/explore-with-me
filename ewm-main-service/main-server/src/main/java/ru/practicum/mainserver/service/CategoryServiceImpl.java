package ru.practicum.mainserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.api.category.CategoryDto;
import ru.practicum.mainserver.api.category.CategoryMapper;
import ru.practicum.mainserver.api.exception.NotFoundException;
import ru.practicum.mainserver.repository.CategoryRepository;
import ru.practicum.mainserver.repository.entity.CategoryEntity;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper mapper;
    private final CategoryRepository repository;

    @Override
    public CategoryEntity add(CategoryDto body) {
        CategoryEntity category = repository.save(
                mapper.entityFromDto(body));
        log.debug("{} was added to db", category);
        return category;
    }

    @Override
    public CategoryEntity update(Long catId, CategoryDto body) {
        CategoryEntity category = get(catId);
        category.setName(body.getName());
        log.debug("Category with id {} was updated, new data: {}", catId, category);
        return category;
    }

    @Override
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
                new NotFoundException("Category with id {} not found"));
        log.debug("Found category: {}", category);
        return category;
    }
}
