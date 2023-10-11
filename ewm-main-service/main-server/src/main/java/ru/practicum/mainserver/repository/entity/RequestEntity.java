package ru.practicum.mainserver.repository.entity;

import lombok.*;
import ru.practicum.mainserver.api.utils.StatusEnum;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "request",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UniqueRequesterForEvent",
                        columnNames = {"requester_id", "event_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {
    private final Instant created = Instant.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @ToString.Exclude
    private UserEntity requester;
    @ManyToOne
    @ToString.Exclude
    private EventEntity event;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Override
    public String toString() {

        String sb = "\nclass RequestEntity {\n" +
                "    id: " + id + "\n" +
                "    requester: " + requester + "\n" +
                "    event: " + event + "\n" +
                "    created: " + created + "\n" +
                "    status: " + status + "\n" +
                "}";
        return sb;
    }
}
