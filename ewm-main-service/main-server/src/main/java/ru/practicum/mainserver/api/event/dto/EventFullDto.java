package ru.practicum.mainserver.api.event.dto;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.mainserver.api.category.CategoryDto;
import ru.practicum.mainserver.api.event.utils.StateEnum;
import ru.practicum.mainserver.api.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * EventFullDto
 */
@Builder
@Getter
public class EventFullDto {
    private final Long id;
    private final String annotation;

    private final CategoryDto category = null;

    private final Long confirmedRequests = null;

    private final LocalDateTime createdOn = null;

    private final String description = null;

    private final LocalDateTime eventDate = null;


    private final UserShortDto initiator = null;

    private final LocationDto locationDto = null;

    private final Boolean paid = null;

    private final Integer participantLimit = 0;

    private final String publishedOn = null;

    private final Boolean requestModeration = true;

    private final String title = null;

    private final Long views = null;
    /**
     * Список состояний жизненного цикла события
     */
    private StateEnum state = null;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EventFullDto {\n");

        sb.append("    annotation: ").append(toIndentedString(annotation)).append("\n");
        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("    confirmedRequests: ").append(toIndentedString(confirmedRequests)).append("\n");
        sb.append("    createdOn: ").append(toIndentedString(createdOn)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    eventDate: ").append(toIndentedString(eventDate)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    initiator: ").append(toIndentedString(initiator)).append("\n");
        sb.append("    location: ").append(toIndentedString(locationDto)).append("\n");
        sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
        sb.append("    participantLimit: ").append(toIndentedString(participantLimit)).append("\n");
        sb.append("    publishedOn: ").append(toIndentedString(publishedOn)).append("\n");
        sb.append("    requestModeration: ").append(toIndentedString(requestModeration)).append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    views: ").append(toIndentedString(views)).append("\n");
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
