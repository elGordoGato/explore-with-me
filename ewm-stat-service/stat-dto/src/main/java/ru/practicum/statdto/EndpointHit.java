package ru.practicum.statdto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@ToString
public class EndpointHit {
    private int id;  //Идентификатор записи

    @NotBlank
    private String app;  //Идентификатор сервиса для которого записывается информация example: ewm-main-service

    @NotBlank
    private String uri;  //URI для которого был осуществлен запрос example: /events/1

    @NotBlank
    private String ip;  //IP-адрес пользователя, осуществившего запрос

    @NotBlank
    private String timestamp;  //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}