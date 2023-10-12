package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.Valid;
import java.util.List;

/**
 * Результат подтверждения/отклонения заявок на участие в событии
 */
@Builder
@AllArgsConstructor
public class EventRequestStatusUpdateResult {
    @JsonProperty("confirmedRequests")
    @Valid
    private List<ParticipationRequestDto> confirmedRequests;

    @JsonProperty("rejectedRequests")
    @Valid
    private List<ParticipationRequestDto> rejectedRequests;

    @Override
    public String toString() {

        String sb = "class EventRequestStatusUpdateResult {\n" +
                "    confirmedRequests: " + toIndentedString(confirmedRequests) + "\n" +
                "    rejectedRequests: " + toIndentedString(rejectedRequests) + "\n" +
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
