package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {
}
