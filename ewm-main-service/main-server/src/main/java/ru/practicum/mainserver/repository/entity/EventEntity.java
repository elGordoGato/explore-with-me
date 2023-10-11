package ru.practicum.mainserver.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "full-event",
        attributeNodes = {
                @NamedAttributeNode("initiator"),
                @NamedAttributeNode("category"),
                @NamedAttributeNode("location"),
        }

)
@NamedEntityGraph(
        name = "short-event",
        attributeNodes = {
                @NamedAttributeNode("initiator"),
                @NamedAttributeNode("category"),
        }
)
public class EventEntity {
    private final Instant createdOn = Instant.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity initiator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CategoryEntity category;

    @Column(nullable = false, length = 2000)
    private String annotation;

    @Column(nullable = false, length = 120, unique = true)
    private String title;

    @Column(nullable = false, length = 7000)
    private String description;

    @Column(nullable = false)
    private Instant eventDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private LocationEntity location;

    private int participantLimit;

    @OneToMany(fetch = FetchType.LAZY)
    private List<RequestEntity> requestEntityList;

    private boolean paid;

    private Instant publishedOn = null;

    private boolean requestModeration = true;

    /**
     * Список состояний жизненного цикла события
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StateEnum state;

    @Override
    public String toString() {

        String sb = "\nclass EventEntity {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    initiator: " + toIndentedString(initiator) + "\n" +
                "    category: " + toIndentedString(category) + "\n" +
                "    annotation: " + toIndentedString(annotation) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    eventDate: " + toIndentedString(eventDate) + "\n" +
                "    location: " + toIndentedString(location) + "\n" +
                "    participantLimit: " + toIndentedString(participantLimit) + "\n" +
                "    paid: " + toIndentedString(paid) + "\n" +
                "    publishedOn: " + toIndentedString(publishedOn) + "\n" +
                "    requestModeration: " + toIndentedString(requestModeration) + "\n" +
                "    createdOn: " + toIndentedString(createdOn) + "\n" +
                "    state: " + toIndentedString(state) + "\n" +
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
