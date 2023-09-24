package ru.practicum.statdto;


public class ViewStats {
    private final String app;     //Название сервиса example: ewm-main-service


    private final String uri;   //URI сервиса example: /events/1


    private final Long hits; //Количество просмотров   example: 6

    public ViewStats(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
