package com.budgety.api.repositories;

import com.budgety.api.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findProfileByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
