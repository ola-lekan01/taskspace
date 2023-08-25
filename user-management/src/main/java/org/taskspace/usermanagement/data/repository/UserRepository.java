package org.taskspace.usermanagement.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taskspace.usermanagement.data.models.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);
}
