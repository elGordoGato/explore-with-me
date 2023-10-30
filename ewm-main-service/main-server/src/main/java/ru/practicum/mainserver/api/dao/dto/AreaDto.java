package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.mainserver.api.dao.dto.event.EventShortDto;
import ru.practicum.mainserver.api.utils.validation.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
public class AreaDto {
    private Long id;
    @NotBlank(groups = Marker.OnCreate.class)
    @Size(min = 2, max = 225, groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private String title;

    @NotNull(groups = Marker.OnCreate.class)
    private LocationDto location;

    @Positive(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @NotNull(groups = Marker.OnCreate.class)
    private Float radius;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EventShortDto> events;

    @Override
    public String toString() {

        String sb = "class AreaDto {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "    location: " + toIndentedString(location) + "\n" +
                "    radius: " + toIndentedString(radius) + "\n" +
                "    events: " + toIndentedString(events) + "\n" +
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
