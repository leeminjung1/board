package com.leeminjung1.domain.model.article;

import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.comment.Comment;
import com.leeminjung1.domain.model.file.File;
import com.leeminjung1.domain.model.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime createdAt;
    private Integer viewCount;
    private Integer voteCount;

    @OneToMany(mappedBy = "article")
    private List<File> files = new ArrayList<>();

    @Builder
    public Article(String title, String content, Category category, Member author) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0;
        this.voteCount = 0;
    }
}
