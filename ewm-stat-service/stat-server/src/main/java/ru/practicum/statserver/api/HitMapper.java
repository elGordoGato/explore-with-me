package ru.practicum.statserver.api;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;
import ru.practicum.statdto.AppName;
import ru.practicum.statdto.EndpointHit;
import ru.practicum.statserver.repository.HitEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

import static java.time.ZoneOffset.UTC;

@Component
public class HitMapper {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
            PATTERN, new Locale("ru_RU"));

    public HitEntity makeHitEntity(EndpointHit hitDto) {
        String dtoApp = hitDto.getApp();
        AppName app = Arrays.stream(AppName.values())
                .filter(appName ->
                        appName.getName().equals(dtoApp))
                .findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                String.format(
                                        "No enum constant with property name %s in %s",
                                        dtoApp, AppName.class)));
        Instant timestamp = LocalDateTime.parse(hitDto.getTimestamp(), FORMATTER).toInstant(UTC);
        return new HitEntity(null,
                app,
                hitDto.getUri(),
                hitDto.getIp(),
                timestamp);
    }

    public Instant getInstant(String dateTime) {
        return LocalDateTime.parse(
                        UriUtils.decode(dateTime, "UTF-8"),
                        FORMATTER)
                .atZone(UTC)
                .toInstant();
    }
}
