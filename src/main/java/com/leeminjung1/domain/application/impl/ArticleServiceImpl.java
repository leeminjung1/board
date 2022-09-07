package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.ArticleService;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
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

    @Override
    public Optional<Article> findById(long articleId) {
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
}
