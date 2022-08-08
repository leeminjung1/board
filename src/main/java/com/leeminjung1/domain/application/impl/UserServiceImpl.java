package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.UserService;
import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.user.User;
import com.leeminjung1.infrastructure.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JpaUserRepository userRepository;

    public void register(RegisterDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setCreatedDate(LocalDateTime.now());
        user.setLastPasswordChanged(LocalDateTime.now());
        userRepository.save(user);
    }

    public User findById(long userId) {
        return userRepository.findById(userId). get();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public void updatePassword(Long id, String password) {
        User user = userRepository.findById(id).get();
        user.setPassword(password);
        user.setLastPasswordChanged(LocalDateTime.now());
    }

}
