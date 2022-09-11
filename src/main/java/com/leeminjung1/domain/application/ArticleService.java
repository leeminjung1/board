package com.leeminjung1.domain.application;

import com.leeminjung1.domain.application.dtos.ArticleListDto;
import com.leeminjung1.domain.model.article.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> findArticleById(long articleId);
    List<ArticleListDto> findAllArticles();
    List<ArticleListDto> findArticlesByCategory(Long categoryId);
    void save(Article article);
}
