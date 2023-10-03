package ru.practicum.mainserver.api.event.utils;

import lombok.Builder;

import java.util.List;

@Builder
public class EventParameters {
    private final String text;
    private final List<Long> users;
    private final List<String> states;
    private final List<Long> categories;
    private final Boolean paid;
    private final String rangeStart;
    private final String rangeEnd;
    private final Boolean onlyAvailable;
    private final SortEnum sort;
}
