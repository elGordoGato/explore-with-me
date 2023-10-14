package ru.practicum.mainserver.api.dao.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @Size(min = 2, max = 250)
    private String name;

    @JsonProperty("email")
    @NotBlank
    @Email
    @Size(min = 6, max = 254)
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
