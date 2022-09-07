package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    Member findByEmail(String email);
}
