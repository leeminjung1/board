package com.leeminjung1.domain.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leeminjung1.domain.application.dtos.ArticleRequestDto;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.comment.Comment;
import com.leeminjung1.domain.model.file.File;
import com.leeminjung1.domain.model.member.Member;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime createdAt;
    private Integer viewCount;
    private Integer likeCount;

    @Formula("(select count(*) from comment c where c.article_id = id)")
    private Long commentCount;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<ArticleLike> likes;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();

    private Byte isNotice;

    @Builder
    public Article(String title, String content, Category category, Member author, LocalDateTime createdAt, Integer viewCount, Integer likeCount, Byte isNotice) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.isNotice = isNotice;
    }

    public void updateViewCount() {
        this.viewCount++;
    }

    public void updateLikeCount() {
        this.likeCount = likes.size();
    }
}
