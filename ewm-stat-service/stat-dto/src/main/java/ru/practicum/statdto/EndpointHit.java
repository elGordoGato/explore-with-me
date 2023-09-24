package ru.practicum.statdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@Table(name = "stats")
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  //Идентификатор записи

    //@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String app;  //Идентификатор сервиса для которого записывается информация example: ewm-main-service

    @Column(nullable = false)
    private String uri;  //URI для которого был осуществлен запрос example: /events/1

    @Column(nullable = false)
    private String ip;  //IP-адрес пользователя, осуществившего запрос

    @Column(nullable = false)
    private Instant timestamp;  //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}
/*@Getter
public class EndpointHit {
    private int id;  //Идентификатор записи

    //@Enumerated(EnumType.STRING)

    private String app;  //Идентификатор сервиса для которого записывается информация example: ewm-main-service


    private String uri;  //URI для которого был осуществлен запрос example: /events/1


    private String ip;  //IP-адрес пользователя, осуществившего запрос


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;  //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")}
}*/
