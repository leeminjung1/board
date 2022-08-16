package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
