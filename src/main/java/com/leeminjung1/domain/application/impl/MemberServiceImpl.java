package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.MemberService;
import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.domain.model.member.RoleName;
import com.leeminjung1.infrastructure.repository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JpaMemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }


    public void register(RegisterDto dto) {
        Member member = Member.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        memberRepository.save(member);
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

}
