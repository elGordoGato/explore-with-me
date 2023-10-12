package ru.practicum.mainserver.api.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
@Setter
public class EventParameters {
    private final String text;
    private final List<Long> users;
    private final List<EventStateEnum> states;
    private final List<Long> categories;
    private final Boolean paid;
    private final Instant rangeStart;
    private final Instant rangeEnd;
    private final boolean onlyAvailable;

    @Override
    public String toString() {

        String sb = "\nclass EventParameters {\n" +
                "    text: " + text + "\n" +
                "    users: " + users + "\n" +
                "    states: " + states + "\n" +
                "    categories: " + categories + "\n" +
                "    paid: " + paid + "\n" +
                "    rangeStart: " + rangeStart + "\n" +
                "    rangeEnd: " + rangeEnd + "\n" +
                "    onlyAvailable: " + onlyAvailable + "\n" +
                "}";
        return sb;
    }
}
