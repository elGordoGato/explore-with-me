package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.mainserver.api.utils.StatusEnum;
import ru.practicum.mainserver.repository.entity.StateEnum;

import java.time.LocalDateTime;

/**
 * Заявка на участие в событии
 */

@Builder
@Getter
public class ParticipationRequestDto {
    @JsonProperty("created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonProperty("event")
    private Long event;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("requester")
    private Long requester;

    @JsonProperty("status")
    private StatusEnum status;

    @Override
    public String toString() {

        String sb = "class ParticipationRequestDto {\n" +
                "    created: " + toIndentedString(created) + "\n" +
                "    event: " + toIndentedString(event) + "\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    requester: " + toIndentedString(requester) + "\n" +
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
