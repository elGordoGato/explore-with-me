package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import ru.practicum.mainserver.api.utils.StatusEnum;
import ru.practicum.mainserver.api.utils.exception.BadRequestException;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */
@Getter
public class EventRequestStatusUpdateRequest {
    @JsonProperty("requestIds")
    @NotNull
    private List<Long> requestIds;
    @JsonProperty("status")
    @NotNull
    private StatusEnum status;

    @AssertTrue
    private boolean isStatusValid() {
        return StatusEnum.CONFIRMED.equals(status) || StatusEnum.REJECTED.equals(status);
    }

    @Override
    public String toString() {

        String sb = "class EventRequestStatusUpdateRequest {\n" +
                "    requestIds: " + toIndentedString(requestIds) + "\n" +
                "    status: " + toIndentedString(status) + "\n" +
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

    public enum StatusActionEnum {
        CONFIRMED("CONFIRMED"),
        REJECTED("REJECTED");

        final String value;

        StatusActionEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StatusActionEnum fromValue(String text) {
            for (StatusActionEnum b : StatusActionEnum.values()) {
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
