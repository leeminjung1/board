package com.leeminjung1.domain.application;

import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.domain.model.member.MemberRole;
import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.infrastructure.repository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BackedLoginService implements UserDetailsService {

    @Autowired
    private JpaMemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return new User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
