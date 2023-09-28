package ru.practicum.statdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppName {
    MAIN("ewm-main-service");

    private final String name;
}
