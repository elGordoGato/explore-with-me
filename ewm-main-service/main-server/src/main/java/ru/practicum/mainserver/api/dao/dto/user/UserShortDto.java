package ru.practicum.mainserver.api.dao.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Пользователь (краткая информация)
 */

@Builder
public class UserShortDto {
    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("name")
    @NotBlank
    private String name;

    @Override
    public String toString() {

        String sb = "\nclass UserShortDto {\n" +
                "    id: " + id + "\n" +
                "    name: " + name + "\n" +
                "}";
        return sb;
    }
}
