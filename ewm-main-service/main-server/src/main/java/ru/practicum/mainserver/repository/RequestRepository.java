package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.api.utils.RequestStatusEnum;
import ru.practicum.mainserver.repository.entity.RequestEntity;

import java.util.Collection;
import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findByEvent_IdAndEvent_Initiator_Id(Long id, Long id1);

    List<RequestEntity> findByEvent_Id(Long id);

    long countByEvent_IdAndStatus(Long id, RequestStatusEnum status);

    List<RequestEntity> findByEvent_IdInAndStatus(Collection<Long> ids, RequestStatusEnum status);

    List<RequestEntity> findByRequester_Id(Long id);
}
