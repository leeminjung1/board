package com.leeminjung1.domain.model.member;

import com.leeminjung1.domain.model.article.Article;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "member")
@Getter @Setter
//@NoArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_password_changed", nullable = false)
    private LocalDateTime lastPasswordChanged;

    /*@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Collection<MemberRole> memberRoles;*/

    @ManyToMany
    @JoinTable(
            name = "members_roles",
            joinColumns = @JoinColumn(
                    name = "member_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

//    @OneToMany(mappedBy = "author")
//    private Collection<Article> articles;

    @Builder
    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdDate = LocalDateTime.now();
        this.lastPasswordChanged = LocalDateTime.now();
    }
}
