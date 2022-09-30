package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
