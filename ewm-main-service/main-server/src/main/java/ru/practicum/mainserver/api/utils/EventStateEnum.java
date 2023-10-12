package ru.practicum.mainserver.api.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EventStateEnum {
    PENDING("PENDING"),
    PUBLISHED("PUBLISHED"),
    CANCELED("CANCELED");

    private final String value;

    EventStateEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static EventStateEnum fromValue(String text) {
        for (EventStateEnum b : EventStateEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}