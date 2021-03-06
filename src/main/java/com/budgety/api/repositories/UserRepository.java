package com.budgety.api.repositories;

import com.budgety.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySub(String sub);
    Optional<User> findById(Long id);
    Boolean existsBySub(String sub);
}
