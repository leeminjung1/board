package com.leeminjung1.domain.application;

import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.member.Member;

import java.util.List;

public interface MemberService {
    void register(RegisterDto dto);
    List<Member> findUsers();
}
