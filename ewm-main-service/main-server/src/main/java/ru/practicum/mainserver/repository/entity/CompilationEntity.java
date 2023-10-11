package ru.practicum.mainserver.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilation")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompilationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<EventEntity> events;

    private boolean pinned;

    @Column(nullable = false, unique = true)
    private String title;
}
