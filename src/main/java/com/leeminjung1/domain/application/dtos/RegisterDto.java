package com.leeminjung1.domain.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String username;
    private String password;

    public RegisterDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
