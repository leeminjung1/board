package com.leeminjung1.domain.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_password_changed", nullable = false)
    private LocalDateTime lastPasswordChanged;

}
