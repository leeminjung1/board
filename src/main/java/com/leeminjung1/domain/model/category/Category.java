package com.leeminjung1.domain.model.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leeminjung1.domain.model.article.Article;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category")
@Getter
@Setter
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

    @Builder
    public Category(Long id, String name, Integer priority, Category parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.depth = (parent.depth == 0 ? 1 : 2);
        this.priority = priority;
    }

    public static Category getRoot() {
        Category category = new Category();
        category.setName("root");
        category.setPriority(0);
        category.setDepth(0);
        category.setParent(null);
        return category;
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
