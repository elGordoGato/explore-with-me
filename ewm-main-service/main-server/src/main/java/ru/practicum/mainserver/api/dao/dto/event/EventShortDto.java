package ru.practicum.mainserver.api.dao.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.mainserver.api.dao.dto.CategoryDto;
import ru.practicum.mainserver.api.dao.dto.user.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Краткая информация о событии
 */
@Builder
@Getter
@AllArgsConstructor
public class EventShortDto {
    @JsonProperty("annotation")
    private String annotation;

    @JsonProperty("category")
    private CategoryDto category;

    @JsonProperty("confirmedRequests")
    private Long confirmedRequests;

    @JsonProperty("eventDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("initiator")
    private UserShortDto initiator;

    @JsonProperty("paid")
    private Boolean paid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("views")
    private Long views;

    public EventShortDto(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventShortDto)) return false;
        EventShortDto that = (EventShortDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {

        String sb = "class EventShortDto {\n" +
                "    annotation: " + toIndentedString(annotation) + "\n" +
                "    category: " + toIndentedString(category) + "\n" +
                "    confirmedRequests: " + toIndentedString(confirmedRequests) + "\n" +
                "    eventDate: " + toIndentedString(eventDate) + "\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    initiator: " + toIndentedString(initiator) + "\n" +
                "    paid: " + toIndentedString(paid) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "    views: " + toIndentedString(views) + "\n" +
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
