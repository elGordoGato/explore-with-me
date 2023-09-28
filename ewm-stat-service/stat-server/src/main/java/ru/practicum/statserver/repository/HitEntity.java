package ru.practicum.statserver.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.statdto.AppName;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "stats")
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  //Идентификатор записи

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppName app;  //Идентификатор сервиса для которого записывается информация example: ewm-main-service

    @Column(nullable = false)
    private String uri;  //URI для которого был осуществлен запрос example: /events/1

    @Column(nullable = false)
    private String ip;  //IP-адрес пользователя, осуществившего запрос

    @Column(nullable = false)
    private Instant timestamp;  //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}
