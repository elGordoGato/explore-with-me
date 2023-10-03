package ru.practicum.mainserver.repository.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "request")
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {
    @ManyToOne
    @ToString.Exclude
    private UserEntity user;
    @ManyToOne
    @ToString.Exclude
    private EventEntity event;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
