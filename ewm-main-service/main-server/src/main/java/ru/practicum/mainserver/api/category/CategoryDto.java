package ru.practicum.mainserver.api.category;

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
        StringBuilder sb = new StringBuilder();
        sb.append("\nclass CategoryDto {\n");

        sb.append("    id: ").append(id).append("\n");
        sb.append("    name: ").append(name).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
