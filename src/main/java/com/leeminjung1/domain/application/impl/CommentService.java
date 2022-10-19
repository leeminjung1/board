package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.dtos.CommentDto;
import com.leeminjung1.domain.application.dtos.CommentListDto;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.comment.Comment;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import com.leeminjung1.infrastructure.repository.CommentRepository;
import com.leeminjung1.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                .commentOrder(commentDto.getCommentLevel() == 0 ? 0 :
                        commentRepository.findFirstByParentIdOrderByCommentOrderDesc(commentDto.getParentId()).getCommentOrder() + 1
                        )
                .parent(commentDto.getParentId() == null ? null : commentRepository.findById(commentDto.getParentId()).orElseThrow())
                .article(articleRepository.findById(articleId).orElseThrow())
                .writer(memberRepository.findById(authorId).orElseThrow())
                .voteCount(0)
                .build();

        commentRepository.save(comment);
    }

    public List<CommentListDto> findAllCommentByMemberId(Long memberId) {
        List<Comment> comments = commentRepository.findAllCommentByMemberId(memberId);
        List<CommentListDto> list = new ArrayList<>();
        for (Comment comment : comments) {
            CommentListDto commentListDto = new CommentListDto(comment);
            list.add(commentListDto);
        }
        return list;
    }

}
