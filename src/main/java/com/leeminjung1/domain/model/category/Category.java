package com.leeminjung1.domain.model.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leeminjung1.domain.model.article.Article;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
//@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "depth", nullable = false)
    private Integer depth;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Article> articles = new ArrayList<>();

    @Formula("(select count(*) from article a where a.category_id = category_id)")
    private int countOfArticles;

    @Builder
    public Category(String name, Integer priority, Integer depth, Category parent) {
        this.name = name;
        this.priority = priority;
        this.depth = depth;
        this.parent = parent;
    }

    public static Category makeRoot() {
        return Category.builder()
                .name("root")
                .priority(0)
                .depth(0)
                .parent(null)
                .build();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", depth=" + depth +
                ", priority=" + priority +
                '}';
    }
}
