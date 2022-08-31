package com.leeminjung1.domain.model.comment;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.file.File;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Byte commentLevel;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private String content;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "reference_id")
    private Comment comment;

    private LocalDateTime createdAt;
    private Integer voteCount;

}
