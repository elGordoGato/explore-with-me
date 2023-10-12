package ru.practicum.mainserver.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainserver.api.utils.RequestStatusEnum;

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
    private UserEntity requester;
    @ManyToOne
    private EventEntity event;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum status;

    @Override
    public String toString() {

        String sb = "\nclass RequestEntity {\n" +
                "    id: " + id + "\n" +
                "    requester: " + requester.getId() + "\n" +
                "    event: " + event.getId() + "\n" +
                "    created: " + created + "\n" +
                "    status: " + status + "\n" +
                "}";
        return sb;
    }
}
