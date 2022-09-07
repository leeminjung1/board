package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.domain.model.member.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
