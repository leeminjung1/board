package com.leeminjung1.domain.application.service.impl;

import com.leeminjung1.domain.application.service.MemberService;
import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.application.dtos.UpdateMemberDto;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.infrastructure.repository.MemberRepository;
import com.leeminjung1.infrastructure.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Long getId(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member.get().getId();
    }

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

    public Optional<Member> findById(Long userId) {
        return memberRepository.findById(userId);//.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
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

    public Member findByEmail(String email) {
        Member member = memberRepository.findByEmail(email).get();
        return member;
    }

    public UpdateMemberDto newUpdateMemberDtoByUserName(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        return new UpdateMemberDto(member.getUsername(), member.getEmail(), member.getPassword(), member.getImgUrl());
    }

    public void updateMember(UpdateMemberDto dto) {
        if (StringUtils.hasText(dto.getPassword())) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            dto.setPassword(null);
        }
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new IllegalArgumentException("non exist email"));
        member.updateMember(dto);
        memberRepository.save(member);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        member.getUsername(),
                        member.getPassword(),
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
    }

/*    public void createPasswordResetTokenForUser(Member member, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, member);
        passwordTokenRepository.save(myToken);
    }*/
}
