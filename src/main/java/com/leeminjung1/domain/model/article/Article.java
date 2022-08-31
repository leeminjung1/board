package com.leeminjung1.domain.model.article;

import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.comment.Comment;
import com.leeminjung1.domain.model.file.File;
import com.leeminjung1.domain.model.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Getter @Setter
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
    private Integer view_count;
    private Integer vote_count;

    @OneToMany(mappedBy = "article")
    private List<File> files = new ArrayList<>();
}
