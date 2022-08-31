package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
}
