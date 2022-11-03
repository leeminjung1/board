package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findFirstByParentIdOrderByCommentOrderDesc(Long parentId);

    @Query(value = "SELECT DISTINCT a FROM Article a JOIN Comment c ON a.id=c.article.id WHERE c.writer.id=:writerId")
    List<Article> findAllArticleByWriterId(Long writerId);

    @Query(value = "SELECT c FROM Comment c JOIN Article a ON a.id=c.article.id WHERE c.writer.id=:memberId")
    List<Comment> findAllCommentByMemberId(Long memberId);

    @Query(value = "SELECT COUNT(*) FROM Comment c JOIN Article a ON a.id=c.article.id where a.id=:articleId")
    Long countCommentByArticleId(Long articleId);

    long countByWriterId(Long writer);
}
