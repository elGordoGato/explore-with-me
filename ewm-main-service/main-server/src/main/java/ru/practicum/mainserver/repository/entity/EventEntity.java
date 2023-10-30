package ru.practicum.mainserver.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainserver.api.utils.EventStateEnum;

import javax.persistence.*;
import java.time.Instant;

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

    private boolean paid;

    private Instant publishedOn = null;

    private boolean requestModeration = true;

    /**
     * Список состояний жизненного цикла события
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStateEnum state;

    @Override
    public String toString() {

        return "\nclass EventEntity {\n" +
                "    id: " + (id) + "\n" +
                "    initiator: " + (initiator.getId()) + "\n" +
                "    category: " + (category.getId()) + "\n" +
                "    annotation: " + (annotation) + "\n" +
                "    title: " + (title) + "\n" +
                "    description: " + (description) + "\n" +
                "    eventDate: " + (eventDate) + "\n" +
                "    location: " + (location.getId()) + "\n" +
                "    participantLimit: " + (participantLimit) + "\n" +
                "    paid: " + (paid) + "\n" +
                "    publishedOn: " + (publishedOn) + "\n" +
                "    requestModeration: " + (requestModeration) + "\n" +
                "    createdOn: " + (createdOn) + "\n" +
                "    state: " + (state) + "\n" +
                "}";
    }
}
