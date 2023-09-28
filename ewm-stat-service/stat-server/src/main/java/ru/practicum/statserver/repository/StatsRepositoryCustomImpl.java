package ru.practicum.statserver.repository;

import ru.practicum.statdto.ViewStats;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.Instant;
import java.util.List;

public class StatsRepositoryCustomImpl implements StatsRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ViewStats> findAllHitsForApp(Instant start, Instant end, List<String> uris, boolean unique) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ViewStats> query = cb.createQuery(ViewStats.class);
        Root<HitEntity> hit = query.from(HitEntity.class);

        Expression<String> app = hit.get("app");
        Expression<String> uri = hit.get("uri");
        Expression<Long> hits = cb.count(hit);

// Создаем предикаты для фильтрации по дате и списку uri
        Predicate datePredicate = cb.between(hit.get("timestamp"), start, end);

// Соединяем предикаты в один с помощью логического И
        Predicate finalPredicate;
        if (uris == null) {
            finalPredicate = datePredicate;
        } else {
            Predicate uriPredicate = uri.in(uris);
            finalPredicate = cb.and(datePredicate, uriPredicate);
        }

// Если нужно учитывать только уникальные посещения
        if (unique) {
            hits = cb.countDistinct(hit.get("ip"));
        }
        query.multiselect(app, uri, hits).where(finalPredicate).groupBy(app, uri);

        query.orderBy(cb.desc(hits));

// Получаем результаты запроса в виде списка массивов объектов
        return entityManager.createQuery(query).getResultList();
    }
}
