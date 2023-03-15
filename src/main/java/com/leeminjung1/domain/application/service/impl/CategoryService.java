package com.leeminjung1.domain.application.service.impl;

import com.leeminjung1.domain.application.dtos.CategoryDto;
import com.leeminjung1.domain.application.dtos.CategoryListDto;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import com.leeminjung1.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category findByCategoryId(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no Category found"));
    }

    public Category findByCategoryName(String name) {
        Category category = categoryRepository.findByName(name);
        return category;
    }

    public Long findCategoryIdByArticleId(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        return article.get().getCategory().getId();
    }

/*    public void newCategory(String name, Long parentId) {
        Optional<Category> parent = categoryRepository.findById(parentId);
        Category category = new Category();
        if (parent.isEmpty()) {     // 최상위 카테고리
            category.setParent(null);
            category.setLevel(0);
        } else {                    // 하위 카테고리
            category.setParent(parent.get());
            category.setLevel(parent.get().getChildren().size());
        }
        category.setName(name);
        categoryRepository.save(category);
    }*/


    @Transactional(readOnly = true)
    public CategoryListDto getCategoryListDto() {
        return new CategoryListDto(
                categoryRepository.findAllByOrderByParentIdAscPriorityAsc().stream()
                        .map(CategoryDto::new)
                        .collect(Collectors.toList()));
    }

    public void updateCategories(List<CategoryDto> categoryDto) {
        for (CategoryDto dto : categoryDto) {
            categoryRepository.update(dto.getPriority(), dto.getDepth(), dto.getName(), dto.getId());
        }
    }

    public void saveCategories(List<CategoryDto> categoryDto) {
        List<Category> list = new ArrayList<>();
        for (CategoryDto dto : categoryDto) {
            list.add(Category.builder()
                    .parent(categoryRepository.findById(dto.getParentId()).get())
                    .depth(dto.getDepth())
                    .priority(dto.getPriority()).name(dto.getName())
                    .build());
        }
        categoryRepository.saveAll(list);
    }

    public void deleteAllById(List<Long> ids) {
        categoryRepository.deleteCategoriesWithIds(ids);
    }
}
