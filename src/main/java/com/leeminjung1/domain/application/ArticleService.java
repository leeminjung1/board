package com.leeminjung1.domain.application;

import com.leeminjung1.domain.model.article.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> findArticleById(long articleId);
    List<Article> findAllArticles();
    List<Article> findArticlesByCategory(Long categoryId);
    void save(Article article);
}
