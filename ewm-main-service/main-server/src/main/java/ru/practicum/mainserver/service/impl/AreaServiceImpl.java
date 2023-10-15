package ru.practicum.mainserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.api.dao.dto.AreaDto;
import ru.practicum.mainserver.api.utils.exception.BadRequestException;
import ru.practicum.mainserver.api.utils.exception.NotFoundException;
import ru.practicum.mainserver.repository.AreaRepository;
import ru.practicum.mainserver.repository.entity.AreaEntity;
import ru.practicum.mainserver.repository.entity.LocationEntity;
import ru.practicum.mainserver.service.AreaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository repository;

    @Override
    @Transactional
    public AreaEntity addNew(AreaEntity area) {
        AreaEntity savedArea = repository.save(area);
        log.debug("Area successfully saved - \n{}", savedArea);
        return savedArea;
    }

    @Override
    @Transactional
    public AreaEntity update(Long areaId, AreaDto body, LocationEntity location) {
        AreaEntity areaToBeUpdated = getById(areaId);
        Optional.ofNullable(body.getTitle())
                .ifPresent(title -> {
                    if (title.isBlank() || title.length() < 2 || 225 < title.length()) {
                        throw new BadRequestException("Title length out of bounds: 2-225, or it is blank; title: " + title);
                    }
                    areaToBeUpdated.setTitle(title);
                });
        Optional.ofNullable(body.getRadius())
                .ifPresent(radius -> {
                    if (radius < 0L) {
                        throw new BadRequestException("Radius must be positive: " + radius);
                    }
                    areaToBeUpdated.setRadius(radius);
                });
        Optional.ofNullable(location)
                .ifPresent(areaToBeUpdated::setLocation);
        AreaEntity updatedArea = repository.save(areaToBeUpdated);
        log.debug("Area with id: {} successfully updated, new data: \n {}", areaId, updatedArea);
        return updatedArea;
    }

    @Override
    public List<AreaEntity> findAll() {
        List<AreaEntity> allAreas = repository.getAllWithLocationBy();
        log.debug("Found all areas: \n{}", allAreas);
        return allAreas;
    }

    @Override
    @Transactional
    public void delete(Long areaId) {
        try {
            repository.deleteById(areaId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(AreaEntity.class, areaId);
        }
        log.debug("Area with id: {} successfully deleted!", areaId);
    }

    @Override
    public AreaEntity getById(Long areaId) {
        AreaEntity foundArea = repository.findOneWithLocationById(areaId).orElseThrow(() ->
                new NotFoundException(AreaEntity.class, areaId));
        log.debug("Area with id: {} successfully found:\n{}", areaId, foundArea);
        return foundArea;
    }


}
