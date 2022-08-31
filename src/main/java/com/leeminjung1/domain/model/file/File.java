package com.leeminjung1.domain.model.file;

import com.leeminjung1.domain.model.article.Article;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String attachType;
    private Integer byteSize;
    private Integer height;
    private Integer width;
    private String name;
    private String originalName;
    private String type;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

}
