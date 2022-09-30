package com.leeminjung1.domain.model.comment;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.file.File;
import com.leeminjung1.domain.model.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Byte commentLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private String content;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    private LocalDateTime createdAt;
    private Integer voteCount;

    @Builder
    public Comment(Byte commentLevel, Member author, Article article, String content, Comment parent, Integer voteCount) {
        this.commentLevel = commentLevel;
        this.author = author;
        this.article = article;
        this.content = content;
        this.parent = parent;
        this.createdAt = LocalDateTime.now();
        this.voteCount = voteCount;
    }
}
