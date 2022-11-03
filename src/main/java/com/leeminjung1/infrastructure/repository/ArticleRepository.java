package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
    Page<Article> findByCategoryIdOrderByIdDesc(Pageable pageable, Long categoryId);
    Page<Article> findByAuthorIdOrderByIdDesc(Pageable pageable,Long authorId);
    Page<Article> findAllByOrderByIdDesc(Pageable pageable);
    List<Article> findTop20ByIsNoticeOrderByIdDesc(Byte isNotice);
    List<Article> findByIsNotice(Byte isNotice);
    long countByCategoryId(Long categoryId);
    long countByAuthorId(Long authorId);
}
