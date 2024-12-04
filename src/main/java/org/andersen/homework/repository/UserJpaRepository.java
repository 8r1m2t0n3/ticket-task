package org.andersen.homework.repository;

import java.util.UUID;
import org.andersen.homework.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, UUID> {
}
