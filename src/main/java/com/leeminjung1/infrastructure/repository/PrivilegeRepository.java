package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.member.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
