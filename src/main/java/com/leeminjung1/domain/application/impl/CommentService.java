package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.dtos.CommentDto;
import com.leeminjung1.domain.model.comment.Comment;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import com.leeminjung1.infrastructure.repository.CommentRepository;
import com.leeminjung1.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public void addComment(Long authorId, Long articleId, CommentDto commentDto) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .commentLevel(commentDto.getCommentLevel())
                .parent(commentDto.getReferenceId() == null ? null : commentRepository.findById(commentDto.getReferenceId()).orElseThrow())
                .article(articleRepository.findById(articleId).orElseThrow())
                .author(memberRepository.findById(authorId).orElseThrow())
                .voteCount(0)
                .build();

        commentRepository.save(comment);
    }
}
