package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.article.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
    List<Article> findByCategoryIdOrderByIdDesc(Long categoryId);
    List<Article> findByAuthorId(Long authorId);

}
