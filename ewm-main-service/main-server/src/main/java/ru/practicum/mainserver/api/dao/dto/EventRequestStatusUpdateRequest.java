package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.practicum.mainserver.api.utils.RequestStatusEnum;

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
    private RequestStatusEnum status;

    @AssertTrue
    private boolean isStatusValid() {
        return RequestStatusEnum.CONFIRMED.equals(status) || RequestStatusEnum.REJECTED.equals(status);
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
}
