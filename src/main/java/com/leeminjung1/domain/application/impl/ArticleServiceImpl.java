package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.ArticleService;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import com.leeminjung1.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Article> findArticleById(long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        return article;
    }

    @Override
    public List<Article> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles;
    }

    @Override
    public List<Article> findArticlesByCategory(Long categoryId) {
        List<Article> articles = articleRepository.findByCategoryId(categoryId);
        return articles;
    }

    public Optional<Category> findCategoryByCategoryId(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category;
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }
}
