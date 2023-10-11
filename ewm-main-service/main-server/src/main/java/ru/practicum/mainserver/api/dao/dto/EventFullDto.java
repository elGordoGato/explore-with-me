package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.mainserver.repository.entity.StateEnum;

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

    private final Long confirmedRequests;
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

    private final Long views;
    /**
     * Список состояний жизненного цикла события
     */
    private StateEnum state;

    @QueryProjection
    public EventFullDto(Long id, String annotation, CategoryDto category, Long confirmedRequests,
                        LocalDateTime createdOn, String description, LocalDateTime eventDate,
                        UserShortDto initiator, LocationDto locationDto, Boolean paid, Integer participantLimit,
                        LocalDateTime publishedOn, Boolean requestModeration, String title, Long views,
                        StateEnum state) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = locationDto;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.title = title;
        this.views = views;
        this.state = state;
    }

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
