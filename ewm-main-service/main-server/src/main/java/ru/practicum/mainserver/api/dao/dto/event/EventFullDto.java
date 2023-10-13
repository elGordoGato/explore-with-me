package ru.practicum.mainserver.api.dao.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.mainserver.api.dao.dto.CategoryDto;
import ru.practicum.mainserver.api.dao.dto.LocationDto;
import ru.practicum.mainserver.api.dao.dto.user.UserShortDto;
import ru.practicum.mainserver.api.utils.EventStateEnum;

import java.time.LocalDateTime;

/**
 * EventFullDto
 */
@Builder
@Getter
public class EventFullDto {
    private final Long id;
    private final String annotation;

    private final CategoryDto category;

    private final long confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdOn;

    private final String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime eventDate;


    private final UserShortDto initiator;

    private final LocationDto location;

    private final Boolean paid;

    private final Integer participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime publishedOn;

    private final Boolean requestModeration;

    private final String title;

    private final long views;
    /**
     * Список состояний жизненного цикла события
     */
    private EventStateEnum state;

    @Override
    public String toString() {

        String sb = "class EventFullDto {\n" +
                "    annotation: " + toIndentedString(annotation) + "\n" +
                "    category: " + toIndentedString(category) + "\n" +
                "    confirmedRequests: " + toIndentedString(confirmedRequests) + "\n" +
                "    createdOn: " + toIndentedString(createdOn) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    eventDate: " + toIndentedString(eventDate) + "\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    initiator: " + toIndentedString(initiator) + "\n" +
                "    location: " + toIndentedString(location) + "\n" +
                "    paid: " + toIndentedString(paid) + "\n" +
                "    participantLimit: " + toIndentedString(participantLimit) + "\n" +
                "    publishedOn: " + toIndentedString(publishedOn) + "\n" +
                "    requestModeration: " + toIndentedString(requestModeration) + "\n" +
                "    state: " + toIndentedString(state) + "\n" +
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
