package ru.practicum.statdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;



@Data
public class EndpointHit {
    private int id;  //Идентификатор записи

    //@Enumerated(EnumType.STRING)

    private String app;  //Идентификатор сервиса для которого записывается информация example: ewm-main-service


    private String uri;  //URI для которого был осуществлен запрос example: /events/1


    private String ip;  //IP-адрес пользователя, осуществившего запрос


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;  //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}