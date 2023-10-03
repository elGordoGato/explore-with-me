package ru.practicum.mainserver.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Пользователь (краткая информация)
 */

@Validated
public class UserShortDto {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    public UserShortDto id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Идентификатор
     *
     * @return id
     **/
    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserShortDto name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Имя
     *
     * @return name
     **/

    @NotNull
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
        UserShortDto userShortDto = (UserShortDto) o;
        return Objects.equals(this.id, userShortDto.id) &&
                Objects.equals(this.name, userShortDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserShortDto {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
