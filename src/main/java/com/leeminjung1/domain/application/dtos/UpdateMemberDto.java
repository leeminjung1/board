package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMemberDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String imgUrl;

    public UpdateMemberDto(Long id, String username, String email, String password, String imgUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.imgUrl = imgUrl;
    }
}