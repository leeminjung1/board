package com.leeminjung1.domain.model.comment;

import com.leeminjung1.domain.model.article.Article;
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
    private Integer commentOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>();

    private LocalDateTime createdAt;
    private Integer voteCount;

    @Builder
    public Comment(Byte commentLevel, Integer commentOrder, Member writer, Article article, String content, File file, Comment parent, Integer voteCount) {
        this.commentLevel = commentLevel;
        this.commentOrder = commentOrder;
        this.writer = writer;
        this.article = article;
        this.content = content;
        this.file = file;
        this.parent = parent;
        this.voteCount = voteCount;
        this.createdAt = LocalDateTime.now();
    }
}
