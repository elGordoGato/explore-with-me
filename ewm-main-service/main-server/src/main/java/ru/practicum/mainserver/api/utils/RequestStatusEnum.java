package ru.practicum.mainserver.api.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestStatusEnum {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    REJECTED("REJECTED"),
    CANCELED("CANCELED");

    private final String value;

    RequestStatusEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static RequestStatusEnum fromValue(String text) {
        for (RequestStatusEnum b : RequestStatusEnum.values()) {
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