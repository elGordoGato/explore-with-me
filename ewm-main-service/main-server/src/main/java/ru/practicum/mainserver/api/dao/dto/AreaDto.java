package ru.practicum.mainserver.api.dao.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AreaDto {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 225)
    private String title;

    @NotNull
    private LocationDto location;

    @Positive
    @NotNull
    private Float radius;

    @Override
    public String toString() {

        String sb = "class CompilationDto {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "    location: " + toIndentedString(location) + "\n" +
                "    radius: " + toIndentedString(radius) + "\n" +
                "}";
        return sb;
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
