package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;
import ru.practicum.mainserver.api.utils.validation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Новое событие
 */
@Getter
@Validated
public class InputDto {
    @JsonProperty("annotation")
    @Size(groups = {Marker.OnCreate.class, Marker.OnUpdate.class}, min = 20, max = 2000)
    @NotNull(groups = {Marker.OnCreate.class})
    private String annotation;

    @JsonProperty("title")
    @Size(groups = {Marker.OnCreate.class, Marker.OnUpdate.class}, min = 3, max = 120)
    @NotNull(groups = {Marker.OnCreate.class})
    private String title;

    @JsonProperty("category")
    @NotNull(groups = {Marker.OnCreate.class})
    private Long category;

    @JsonProperty("description")
    @Size(groups = {Marker.OnCreate.class, Marker.OnUpdate.class}, min = 20, max = 7000)
    @NotNull(groups = {Marker.OnCreate.class})
    private String description;

    @JsonProperty("eventDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(groups = {Marker.OnCreate.class})
    @FutureIn2Hours(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private LocalDateTime eventDate;

    @JsonProperty("location")
    @NotNull(groups = {Marker.OnCreate.class})
    private LocationDto locationDto;

    @JsonProperty("paid")
    private Boolean paid;

    @JsonProperty("participantLimit")
    @PositiveOrZero(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private Integer participantLimit;

    @JsonProperty("requestModeration")
    private Boolean requestModeration;

    @JsonProperty("stateAction")
    @NotNull(groups = Marker.OnUpdate.class)
    @StateByUser(groups = StateUserValidator.class)
    @StateByAdmin(groups = StateAdminValidator.class)
    private StateActionEnum stateAction;



    @Override
    public String toString() {

        String sb = "\nclass InputDto {\n" +
                "    annotation: " + toIndentedString(annotation) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "    category: " + toIndentedString(category) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    eventDate: " + toIndentedString(eventDate) + "\n" +
                "    location: " + toIndentedString(locationDto) + "\n" +
                "    paid: " + toIndentedString(paid) + "\n" +
                "    participantLimit: " + toIndentedString(participantLimit) + "\n" +
                "    requestModeration: " + toIndentedString(requestModeration) + "\n" +
                "    stateAction: " + toIndentedString(stateAction) + "\n" +
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

    public enum StateActionEnum {
        PUBLISH_EVENT("PUBLISH_EVENT"),

        REJECT_EVENT("REJECT_EVENT"),

        SEND_TO_REVIEW("SEND_TO_REVIEW"),
        CANCEL_REVIEW("CANCEL_REVIEW");

        final String value;

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
