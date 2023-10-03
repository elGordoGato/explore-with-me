package ru.practicum.mainserver.api.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.mainserver.api.event.dto.EventShortDto;

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
        StringBuilder sb = new StringBuilder();
        sb.append("class CompilationDto {\n");

        sb.append("    events: ").append(toIndentedString(events)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    pinned: ").append(toIndentedString(pinned)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
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
