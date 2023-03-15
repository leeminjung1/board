package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.article.ArticleLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    @Modifying
    @Query(value = "INSERT INTO article_like(article_id, member_id) VALUES(:articleId, :memberId)", nativeQuery = true)
    int like(Long articleId, Long memberId);

    @Modifying
    @Query(value = "DELETE FROM article_like WHERE article_id = :articleId AND member_id = :memberId", nativeQuery = true)
    int unlike(Long articleId, Long memberId);

    List<ArticleLike> findAllByArticleId(Long articleId);

    @Query(value = "SELECT a FROM Article a JOIN ArticleLike al ON a.id=al.article.id WHERE al.member.id=:memberId")
    Page<Article> findArticleByMemberId(Pageable pageable, Long memberId);

    long countByMemberId(Long memberId);

    Optional<ArticleLike> findByArticleIdAndMemberId(Long articleId, Long memberId);
}
