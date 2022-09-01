package com.leeminjung1.domain.application;

import com.leeminjung1.domain.model.article.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> findById(long articleId);
    List<Article> findArticles();
}
