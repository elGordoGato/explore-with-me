package ru.practicum.mainserver.api.event.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
 */
@Validated


public class UpdateEventAdminRequest {
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
    private Boolean paid = null;

    @JsonProperty("participantLimit")
    private Integer participantLimit = null;

    @JsonProperty("requestModeration")
    private Boolean requestModeration = null;
    @JsonProperty("stateAction")
    private StateActionEnum stateAction = null;
    @JsonProperty("title")
    private String title = null;

    public UpdateEventAdminRequest annotation(String annotation) {
        this.annotation = annotation;
        return this;
    }

    /**
     * Новая аннотация
     *
     * @return annotation
     **/

    @Size(min = 20, max = 2000)
    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public UpdateEventAdminRequest category(Long category) {
        this.category = category;
        return this;
    }

    /**
     * Новая категория
     *
     * @return category
     **/

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public UpdateEventAdminRequest description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Новое описание
     *
     * @return description
     **/

    @Size(min = 20, max = 7000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UpdateEventAdminRequest eventDate(String eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    /**
     * Новые дата и время на которые намечено событие. Дата и время указываются в формате \"yyyy-MM-dd HH:mm:ss\"
     *
     * @return eventDate
     **/

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public UpdateEventAdminRequest location(LocationDto locationDto) {
        this.locationDto = locationDto;
        return this;
    }

    /**
     * Get location
     *
     * @return location
     **/

    @Valid
    public LocationDto getLocation() {
        return locationDto;
    }

    public void setLocation(LocationDto locationDto) {
        this.locationDto = locationDto;
    }

    public UpdateEventAdminRequest paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    /**
     * Новое значение флага о платности мероприятия
     *
     * @return paid
     **/

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public UpdateEventAdminRequest participantLimit(Integer participantLimit) {
        this.participantLimit = participantLimit;
        return this;
    }

    /**
     * Новый лимит пользователей
     *
     * @return participantLimit
     **/

    public Integer getParticipantLimit() {
        return participantLimit;
    }

    public void setParticipantLimit(Integer participantLimit) {
        this.participantLimit = participantLimit;
    }

    public UpdateEventAdminRequest requestModeration(Boolean requestModeration) {
        this.requestModeration = requestModeration;
        return this;
    }

    /**
     * Нужна ли пре-модерация заявок на участие
     *
     * @return requestModeration
     **/

    public Boolean isRequestModeration() {
        return requestModeration;
    }

    public void setRequestModeration(Boolean requestModeration) {
        this.requestModeration = requestModeration;
    }

    public UpdateEventAdminRequest stateAction(StateActionEnum stateAction) {
        this.stateAction = stateAction;
        return this;
    }

    /**
     * Новое состояние события
     *
     * @return stateAction
     **/

    public StateActionEnum getStateAction() {
        return stateAction;
    }

    public void setStateAction(StateActionEnum stateAction) {
        this.stateAction = stateAction;
    }

    public UpdateEventAdminRequest title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Новый заголовок
     *
     * @return title
     **/

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
        UpdateEventAdminRequest updateEventAdminRequest = (UpdateEventAdminRequest) o;
        return Objects.equals(this.annotation, updateEventAdminRequest.annotation) &&
                Objects.equals(this.category, updateEventAdminRequest.category) &&
                Objects.equals(this.description, updateEventAdminRequest.description) &&
                Objects.equals(this.eventDate, updateEventAdminRequest.eventDate) &&
                Objects.equals(this.locationDto, updateEventAdminRequest.locationDto) &&
                Objects.equals(this.paid, updateEventAdminRequest.paid) &&
                Objects.equals(this.participantLimit, updateEventAdminRequest.participantLimit) &&
                Objects.equals(this.requestModeration, updateEventAdminRequest.requestModeration) &&
                Objects.equals(this.stateAction, updateEventAdminRequest.stateAction) &&
                Objects.equals(this.title, updateEventAdminRequest.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, description, eventDate, locationDto, paid, participantLimit, requestModeration, stateAction, title);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateEventAdminRequest {\n");

        sb.append("    annotation: ").append(toIndentedString(annotation)).append("\n");
        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    eventDate: ").append(toIndentedString(eventDate)).append("\n");
        sb.append("    location: ").append(toIndentedString(locationDto)).append("\n");
        sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
        sb.append("    participantLimit: ").append(toIndentedString(participantLimit)).append("\n");
        sb.append("    requestModeration: ").append(toIndentedString(requestModeration)).append("\n");
        sb.append("    stateAction: ").append(toIndentedString(stateAction)).append("\n");
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

    /**
     * Новое состояние события
     */
    public enum StateActionEnum {
        PUBLISH_EVENT("PUBLISH_EVENT"),

        REJECT_EVENT("REJECT_EVENT");

        private String value;

        StateActionEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StateActionEnum fromValue(String text) {
            for (StateActionEnum b : StateActionEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }
}
