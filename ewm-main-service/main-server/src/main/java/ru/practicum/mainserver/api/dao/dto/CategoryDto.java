package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Категория
 */
@Builder
@Getter
public class CategoryDto {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("name")
    @NotBlank
    @Size(min = 1, max = 50)
    private final String name;

    @Override
    public String toString() {

        String sb = "\nclass CategoryDto {\n" +
                "    id: " + id + "\n" +
                "    name: " + name + "\n" +
                "}";
        return sb;
    }
}
