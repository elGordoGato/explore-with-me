package ru.practicum.mainserver.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Заявка на участие в событии
 */
@Validated
public class ParticipationRequestDto {
    @JsonProperty("created")
    private String created = null;

    @JsonProperty("event")
    private Long event = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("requester")
    private Long requester = null;

    @JsonProperty("status")
    private String status = null;

    public ParticipationRequestDto created(String created) {
        this.created = created;
        return this;
    }

    /**
     * Дата и время создания заявки
     *
     * @return created
     **/

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public ParticipationRequestDto event(Long event) {
        this.event = event;
        return this;
    }

    /**
     * Идентификатор события
     *
     * @return event
     **/

    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public ParticipationRequestDto id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Идентификатор заявки
     *
     * @return id
     **/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParticipationRequestDto requester(Long requester) {
        this.requester = requester;
        return this;
    }

    /**
     * Идентификатор пользователя, отправившего заявку
     *
     * @return requester
     **/

    public Long getRequester() {
        return requester;
    }

    public void setRequester(Long requester) {
        this.requester = requester;
    }

    public ParticipationRequestDto status(String status) {
        this.status = status;
        return this;
    }

    /**
     * Статус заявки
     *
     * @return status
     **/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipationRequestDto participationRequestDto = (ParticipationRequestDto) o;
        return Objects.equals(this.created, participationRequestDto.created) &&
                Objects.equals(this.event, participationRequestDto.event) &&
                Objects.equals(this.id, participationRequestDto.id) &&
                Objects.equals(this.requester, participationRequestDto.requester) &&
                Objects.equals(this.status, participationRequestDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, event, id, requester, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ParticipationRequestDto {\n");

        sb.append("    created: ").append(toIndentedString(created)).append("\n");
        sb.append("    event: ").append(toIndentedString(event)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    requester: ").append(toIndentedString(requester)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
