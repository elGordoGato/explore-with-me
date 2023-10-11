package ru.practicum.statdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@Builder
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