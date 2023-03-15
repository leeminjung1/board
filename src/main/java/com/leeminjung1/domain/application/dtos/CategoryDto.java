package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Builder
public class CategoryDto {
    private Long id;
    private String name;
    private Integer depth;
    private Integer priority;
    private Long parentId;
    private Integer entries;   // article count
    private List<CategoryDto> children;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.priority = category.getPriority();
        this.parentId = category.getParent() == null ? 1 : getId();
        this.depth = category.getDepth();
        this.entries = category.getCountOfArticles();
        this.children = depth == 1 ? makeChildren(category) : new ArrayList<>();
    }

    public static List<CategoryDto> makeChildren(Category category) {
        return category.getChildren().stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
    }
}
