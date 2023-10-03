package ru.practicum.mainserver.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Данные нового пользователя
 */
@Validated
public class NewUserRequest {
    @JsonProperty("email")
    private String email = null;

    @JsonProperty("name")
    private String name = null;

    public NewUserRequest email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Почтовый адрес
     *
     * @return email
     **/
    @NotNull

    @Size(min = 6, max = 254)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NewUserRequest name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Имя
     *
     * @return name
     **/
    @NotNull

    @Size(min = 2, max = 250)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewUserRequest newUserRequest = (NewUserRequest) o;
        return Objects.equals(this.email, newUserRequest.email) &&
                Objects.equals(this.name, newUserRequest.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NewUserRequest {\n");

        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
