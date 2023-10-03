package ru.practicum.mainserver.api.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Новое событие
 */
@Validated


public class NewEventDto {
    @JsonProperty("annotation")
    private String annotation = null;

    @JsonProperty("category")
    private Long category = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("eventDate")
    private String eventDate = null;

    @JsonProperty("location")
    private LocationDto locationDto = null;

    @JsonProperty("paid")
    private Boolean paid = false;

    @JsonProperty("participantLimit")
    private Integer participantLimit = 0;

    @JsonProperty("requestModeration")
    private Boolean requestModeration = true;

    @JsonProperty("title")
    private String title = null;

    public NewEventDto annotation(String annotation) {
        this.annotation = annotation;
        return this;
    }

    /**
     * Краткое описание события
     *
     * @return annotation
     **/
    @NotNull

    @Size(min = 20, max = 2000)
    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public NewEventDto category(Long category) {
        this.category = category;
        return this;
    }

    /**
     * id категории к которой относится событие
     *
     * @return category
     **/
    @NotNull

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public NewEventDto description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Полное описание события
     *
     * @return description
     **/
    @NotNull

    @Size(min = 20, max = 7000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NewEventDto eventDate(String eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    /**
     * Дата и время на которые намечено событие. Дата и время указываются в формате \"yyyy-MM-dd HH:mm:ss\"
     *
     * @return eventDate
     **/
    @NotNull

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public NewEventDto location(LocationDto locationDto) {
        this.locationDto = locationDto;
        return this;
    }

    /**
     * Get location
     *
     * @return location
     **/
    @NotNull

    @Valid
    public LocationDto getLocation() {
        return locationDto;
    }

    public void setLocation(LocationDto locationDto) {
        this.locationDto = locationDto;
    }

    public NewEventDto paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    /**
     * Нужно ли оплачивать участие в событии
     *
     * @return paid
     **/

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public NewEventDto participantLimit(Integer participantLimit) {
        this.participantLimit = participantLimit;
        return this;
    }

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     *
     * @return participantLimit
     **/

    public Integer getParticipantLimit() {
        return participantLimit;
    }

    public void setParticipantLimit(Integer participantLimit) {
        this.participantLimit = participantLimit;
    }

    public NewEventDto requestModeration(Boolean requestModeration) {
        this.requestModeration = requestModeration;
        return this;
    }

    /**
     * Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически.
     *
     * @return requestModeration
     **/

    public Boolean isRequestModeration() {
        return requestModeration;
    }

    public void setRequestModeration(Boolean requestModeration) {
        this.requestModeration = requestModeration;
    }

    public NewEventDto title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Заголовок события
     *
     * @return title
     **/
    @NotNull

    @Size(min = 3, max = 120)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewEventDto newEventDto = (NewEventDto) o;
        return Objects.equals(this.annotation, newEventDto.annotation) &&
                Objects.equals(this.category, newEventDto.category) &&
                Objects.equals(this.description, newEventDto.description) &&
                Objects.equals(this.eventDate, newEventDto.eventDate) &&
                Objects.equals(this.locationDto, newEventDto.locationDto) &&
                Objects.equals(this.paid, newEventDto.paid) &&
                Objects.equals(this.participantLimit, newEventDto.participantLimit) &&
                Objects.equals(this.requestModeration, newEventDto.requestModeration) &&
                Objects.equals(this.title, newEventDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, description, eventDate, locationDto, paid, participantLimit, requestModeration, title);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NewEventDto {\n");

        sb.append("    annotation: ").append(toIndentedString(annotation)).append("\n");
        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    eventDate: ").append(toIndentedString(eventDate)).append("\n");
        sb.append("    location: ").append(toIndentedString(locationDto)).append("\n");
        sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
        sb.append("    participantLimit: ").append(toIndentedString(participantLimit)).append("\n");
        sb.append("    requestModeration: ").append(toIndentedString(requestModeration)).append("\n");
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
