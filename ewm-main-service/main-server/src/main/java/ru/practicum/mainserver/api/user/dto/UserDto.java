package ru.practicum.mainserver.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Пользователь
 */

@Builder
@Getter
public class UserDto {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    @NotBlank
    private String name = null;

    @JsonProperty("email")
    @NotBlank
    private String email = null;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nclass UserDto {\n");

        sb.append("    id: ").append(id).append("\n");
        sb.append("    name: ").append(name).append("\n");
        sb.append("    email: ").append(email).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
