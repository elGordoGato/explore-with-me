package ru.practicum.statserver;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.statdto.EndpointHit;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;


public class EntityEndpointHit //extends ru.practicum.statdto.EndpointHit
{
/*    @Id
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
    private Instant timestamp;  //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")}

    public EntityEndpointHit (EndpointHit hit){
        this.app = hit.getApp();
        this.ip = hit.getIp();
        this.uri = hit.getUri();
        this.timestamp = hit.getTimestamp().atZone(UTC).toInstant();
    }*/
}
