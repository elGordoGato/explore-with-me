package ru.practicum.mainserver.api.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.api.dao.dto.CategoryDto;
import ru.practicum.mainserver.api.dao.mapper.CategoryMapper;
import ru.practicum.mainserver.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody @Valid CategoryDto body) {
        log.debug("Received request from admin to create category: {}", body);
        return categoryMapper.dtoFromEntity(categoryService.add(body));
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable("catId") Long catId,
                                      @RequestBody @Valid CategoryDto body) {
        log.debug("Received request from admin to update category with id: {} with new data: {}", catId, body);
        return categoryMapper.dtoFromEntity(categoryService.update(catId, body));
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catId") Long catId) {
        log.debug("Received request from admin to delete category with id: {}", catId);
        categoryService.delete(catId);
    }
}
