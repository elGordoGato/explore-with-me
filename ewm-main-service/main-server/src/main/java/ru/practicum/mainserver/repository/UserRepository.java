package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.repository.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
