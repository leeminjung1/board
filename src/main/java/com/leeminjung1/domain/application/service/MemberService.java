package com.leeminjung1.domain.application.service;

import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.member.Member;

import java.util.List;

public interface MemberService {
    void registerAdmin(RegisterDto dto);
    void registerUser(RegisterDto dto);
    List<Member> findUsers();
}
