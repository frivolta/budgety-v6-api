package com.budgety.api.repository;

import com.budgety.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySub(String sub);
    Boolean existsBySub(String sub);
}
