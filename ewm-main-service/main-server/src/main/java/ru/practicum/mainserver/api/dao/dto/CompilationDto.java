package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Подборка событий
 */

@Builder
@Getter
public class CompilationDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("events")
    @Valid
    private List<EventShortDto> events;

    @JsonProperty("pinned")
    @NotNull
    private Boolean pinned;

    @JsonProperty("title")
    @NotBlank
    private String title;

    @Override
    public String toString() {

        String sb = "class CompilationDto {\n" +
                "    events: " + toIndentedString(events) + "\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    pinned: " + toIndentedString(pinned) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
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
