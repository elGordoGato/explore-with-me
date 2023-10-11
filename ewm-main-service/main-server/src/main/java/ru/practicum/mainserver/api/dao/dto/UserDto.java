package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * Пользователь
 */

@Builder
@Getter
public class UserDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("email")
    @NotBlank
    private String email;

    @Override
    public String toString() {

        String sb = "\nclass UserDto {\n" +
                "    id: " + id + "\n" +
                "    name: " + name + "\n" +
                "    email: " + email + "\n" +
                "}";
        return sb;
    }
}
