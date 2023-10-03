package ru.practicum.mainserver.api.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        log.debug("Received public request to get all categories from #{} and quantity: {}",
                from, size);
        return categoryMapper.dtoFromEntityList(categoryService.get(from, size));
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable("catId") Long catId) {
        log.debug("Received public request to get category with id: {}",
                catId);
        return categoryMapper.dtoFromEntity(categoryService.get(catId));
    }

}
