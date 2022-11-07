package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findFirstByParentIdOrderByCommentOrderDesc(Long parentId);

    @Query(value = "SELECT DISTINCT a FROM Article a JOIN Comment c ON a.id=c.article.id WHERE c.writer.id=:writerId order by a.id desc")
    Page<Article> findArticlesByWriterIdOrderByIdDesc(Pageable pageable, Long writerId);

    Page<Comment> findByWriterIdOrderByIdDesc(Pageable pageable, Long writerId);

    @Query(value = "SELECT COUNT(*) FROM Comment c JOIN Article a ON a.id=c.article.id where a.id=:articleId")
    Long countCommentByArticleId(Long articleId);

    long countByWriterId(Long writerId);

    long countByParentId(Long commentId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Comment c SET c.content=null, c.createdAt=null, voteCount=null, c.file.id=null, c.writer.id=null WHERE c.id = :commentId")
    void deleteCommentWhereChildrenExist(Long commentId);
}
