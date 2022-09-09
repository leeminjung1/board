package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.MemberService;
import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.infrastructure.repository.MemberRepository;
import com.leeminjung1.infrastructure.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerAdmin(RegisterDto dto) {
        Member user = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail()).build();
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));

        memberRepository.save(user);
    }

    public void registerUser(RegisterDto dto) {
        Member user = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail()).build();
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

        memberRepository.save(user);
    }

    public Member findById(long userId) {
        return memberRepository.findById(userId). get();
    }

    public List<Member> findUsers() {
        return memberRepository.findAll();
    }

    public void updatePassword(Long id, String password) {
        Member member = memberRepository.findById(id).get();
        member.setPassword(password);
        member.setLastPasswordChanged(LocalDateTime.now());
    }

    public Member findByUsername(String username) {
        Member member = memberRepository.findByUsername(username).get();
        return member;
    }
}
