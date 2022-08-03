package com.leeminjung1.domain.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String email;
    private String username;
    private String password;
}
