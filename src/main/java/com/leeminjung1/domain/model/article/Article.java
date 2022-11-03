package com.leeminjung1.domain.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime createdAt;
    private Integer viewCount;
    private Integer likeCount;

    @Formula("(select count(*) from comment c where c.article_id = id)")
    private Long commentCount;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private Collection<ArticleLike> likes;

    @OneToMany(mappedBy = "article")
    private List<File> files = new ArrayList<>();

    private Byte isNotice;

    @Builder
    public Article(String title, String content, Category category, Member author, LocalDateTime createdAt, Integer viewCount, Integer likeCount, Byte isNotice) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        this.createdAt = createdAt;
        this.viewCount = 0;
        this.likeCount = 0;
        this.isNotice = isNotice;
    }

    public void updateViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
