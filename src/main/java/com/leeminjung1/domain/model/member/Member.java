package com.leeminjung1.domain.model.member;

import com.leeminjung1.domain.application.dtos.UpdateMemberDto;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.comment.Comment;
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

    @Column(name = "imgUrl")
    private String imgUrl;

    private boolean enabled;

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

    @OneToMany(mappedBy = "author")
    private Collection<Article> articles;

    @OneToMany(mappedBy = "author")
    private Collection<Comment> comments;

    @Builder
    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imgUrl = "cafe_profile_363.png";
        this.createdDate = LocalDateTime.now();
        this.lastPasswordChanged = LocalDateTime.now();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void updateMember(UpdateMemberDto dto) {
        this.username = dto.getUsername();
        if (dto.getPassword() != null) {
            this.password = dto.getPassword();
        }
        if (dto.getImgUrl() != null) {
            this.imgUrl = dto.getImgUrl();
        }
    }
}
