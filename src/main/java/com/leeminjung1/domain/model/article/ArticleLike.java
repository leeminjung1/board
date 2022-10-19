package com.leeminjung1.domain.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leeminjung1.domain.model.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table
@Getter
public class ArticleLike {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @JsonIgnore
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

}
