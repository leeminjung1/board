package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMemberDto {

    private String username;
    private String email;
    private String password;
    private String imgUrl;

    public UpdateMemberDto(String username, String email, String password, String imgUrl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "UpdateMemberDto{" +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}