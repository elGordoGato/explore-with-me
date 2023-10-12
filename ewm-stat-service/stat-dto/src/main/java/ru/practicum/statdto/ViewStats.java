package ru.practicum.statdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats {
    @NotBlank
    private String app;   //Название сервиса example: ewm-main-service

    @NotBlank
    private String uri;   //URI сервиса example: /events/1

    @NotNull
    private long hits;   //Количество просмотров   example: 6

    public ViewStats(AppName app, String uri, long hits) {
        this.app = app.getName();
        this.uri = uri;
        this.hits = hits;
    }

    @Override
    public String toString() {

        String sb = "class ViewStats {\n" +
                "    app: " + app + "\n" +
                "    uri: " + uri + "\n" +
                "    hits: " + hits + "\n" +
                "}";
        return sb;
    }
}
