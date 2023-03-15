package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    @Query(value = "select count(distinct a.id) from Article a join Comment c on a.id=c.article.id where c.writer.id=:writerId order by a.id desc")
    long countArticleCommentedByWriterId(Long writerId);

    void deleteByIdIn(List<Long> ids);

    @Modifying
    @Query("delete from Article a where a.id in ?1")
    void deleteUsersWithIds(List<Long> ids);
}
