package ru.practicum.mainserver.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "compilation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<EventEntity> events;

    private boolean pinned;

    @Column(nullable = false, unique = true)
    private String title;

    @OneToOne
    private AreaEntity area;

    @Override
    public String toString() {
        String sb = "\nclass CompilationEntity {\n" +
                "    id: " + id + "\n" +
                "    title: " + title + "\n" +
                "    events: " + events.stream().map(EventEntity::getId).collect(Collectors.toList()) + "\n" +
                "    pinned: " + pinned + "\n" +
                "}";
        return sb;
    }
}
