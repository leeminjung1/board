package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.MemberService;
import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.member.Member;
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

    public void register(RegisterDto dto) {
        Member user = new Member();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setCreatedDate(LocalDateTime.now());
        user.setLastPasswordChanged(LocalDateTime.now());
        memberRepository.save(user);
    }

    public Member findById(long userId) {
        return memberRepository.findById(userId). get();
    }

    @Override
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
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
