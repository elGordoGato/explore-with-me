package ru.practicum.mainserver.repository;

import ru.practicum.mainserver.api.utils.EventParameters;
import ru.practicum.mainserver.repository.entity.EventEntity;

import java.util.List;

public interface EventRepositoryCustom {

    List<Long> findIdsByParams(EventParameters parameters);

    List<EventEntity> findEventsByParams(boolean isFull, EventParameters parameters, int from, int size);
}
