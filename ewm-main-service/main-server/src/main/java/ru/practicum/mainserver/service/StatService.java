package ru.practicum.mainserver.service;

import java.util.List;
import java.util.Map;

public interface StatService {

    void saveHit(String ip, String uri);

    Map<Long, Long> getMap(List<Long> eventsIds);

    Long getViews(Long id);
}
