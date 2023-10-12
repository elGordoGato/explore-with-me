package ru.practicum.mainserver.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ru.practicum.mainserver.api.utils.EventParameters;
import ru.practicum.mainserver.api.utils.EventStateEnum;
import ru.practicum.mainserver.api.utils.RequestStatusEnum;
import ru.practicum.mainserver.repository.entity.EventEntity;
import ru.practicum.mainserver.repository.entity.QEventEntity;
import ru.practicum.mainserver.repository.entity.QRequestEntity;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    @PersistenceContext
    private final EntityManager entityManager;
    private final QEventEntity EVENT = QEventEntity.eventEntity;
    private final QRequestEntity REQUEST = QRequestEntity.requestEntity;


    @Override
    public List<Long> findIdsByParams(EventParameters parameters) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory
                .select(EVENT.id)
                .from(EVENT)
                .where(commonCondition(parameters))
                .fetch();
    }

    @Override
    public List<EventEntity> findEventsByParams(boolean isFull, EventParameters parameters, int from, int size) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        EntityGraph entityGraph = isFull ?
                entityManager.getEntityGraph("full-event") :
                entityManager.getEntityGraph("short-event");

        return queryFactory
                .select(EVENT)
                .from(EVENT)
                .setHint("jakarta.persistence.loadgraph", entityGraph)
                .where(commonCondition(parameters))
                .offset(from)
                .limit(size)
                .orderBy(isFull ? EVENT.id.asc() : EVENT.eventDate.asc())
                .fetch();
    }

    private BooleanExpression commonCondition(EventParameters parameters) {
        return eventDateGreater(parameters.getRangeStart())
                .and(eventDateLess(
                        parameters.getRangeEnd()))
                .and(containsText(
                        parameters.getText()))
                .and(initiatorIn(
                        parameters.getUsers()))
                .and(stateIn(
                        parameters.getStates()))
                .and(categoryIdIn(
                        parameters.getCategories()))
                .and(paidEq(
                        parameters.getPaid()))
                .and(onlyAvailable(
                        parameters.isOnlyAvailable()));
    }

    private BooleanExpression containsText(String text) {
        return text != null ?
                EVENT.annotation.containsIgnoreCase(text)
                        .or(EVENT.description.containsIgnoreCase(text)) :
                null;
    }

    private BooleanExpression initiatorIn(List<Long> initiatorsId) {
        return initiatorsId != null ? EVENT.initiator.id.in(initiatorsId) : null;
    }

    private BooleanExpression stateIn(List<EventStateEnum> states) {
        return states != null ? EVENT.state.in(states) : null;
    }

    private BooleanExpression categoryIdIn(List<Long> categoriesId) {
        return categoriesId != null ? EVENT.category.id.in(categoriesId) : null;
    }

    private BooleanExpression paidEq(Boolean paid) {
        return paid != null ? EVENT.paid.eq(paid) : null;
    }

    private BooleanExpression eventDateGreater(Instant rangeStart) {
        return EVENT.eventDate.gt(rangeStart);
    }

    private BooleanExpression eventDateLess(Instant rangeEnd) {
        return rangeEnd != null ? EVENT.eventDate.lt(rangeEnd) : null;
    }

    private BooleanExpression onlyAvailable(boolean available) {
        return available ?
                EVENT.participantLimit.gt(
                        (REQUEST.event.id.eq(EVENT.id)
                                .and(
                                        REQUEST.status.eq(RequestStatusEnum.CONFIRMED)))
                                .count())
                : null;
    }
}
