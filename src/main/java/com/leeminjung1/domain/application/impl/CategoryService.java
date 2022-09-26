package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.dtos.CategoryDto;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import com.leeminjung1.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getCategoryDto() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryDto categoryDto = new CategoryDto(category, category.getArticles().size());
            categoryDtos.add(categoryDto);
        }

        return categoryDtos;
    }
}
