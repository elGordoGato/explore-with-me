package ru.practicum.mainserver.repository.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.mainserver.api.event.utils.StateEnum;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private UserEntity initiator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private LocationEntity location;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private CategoryEntity category;

    private Instant createdOn = Instant.now();

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String eventDate;

    private boolean paid;

    private int participantLimit = 0;

    private Instant publishedOn = null;

    private boolean requestModeration = true;

    /**
     * Список состояний жизненного цикла события
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StateEnum state = StateEnum.PENDING;

    private String title;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EventFullDto {\n");

        sb.append("    annotation: ").append(toIndentedString(annotation)).append("\n");
        sb.append("    createdOn: ").append(toIndentedString(createdOn)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    eventDate: ").append(toIndentedString(eventDate)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
        sb.append("    participantLimit: ").append(toIndentedString(participantLimit)).append("\n");
        sb.append("    publishedOn: ").append(toIndentedString(publishedOn)).append("\n");
        sb.append("    requestModeration: ").append(toIndentedString(requestModeration)).append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
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
