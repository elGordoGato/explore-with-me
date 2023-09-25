package ru.practicum.statdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewStats {
    private String app;     //Название сервиса example: ewm-main-service


    private String uri;   //URI сервиса example: /events/1


    private Long hits; //Количество просмотров   example: 6

    public ViewStats(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
