package com.leeminjung1.domain.application;

import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.user.User;

public interface UserService {

    User findById(long userId);
    void register(RegisterDto dto);
}
