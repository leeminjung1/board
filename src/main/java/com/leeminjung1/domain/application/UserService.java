package com.leeminjung1.domain.application;

import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.user.User;

import java.util.List;

public interface UserService {
    User findById(long userId);
    User findByUsername(String username);
    void register(RegisterDto dto);
    List<User> findUsers();
    void updatePassword(Long id, String password);
}
