package com.leeminjung1.domain.application.service.impl;

import com.leeminjung1.domain.application.dtos.CommentDto;
import com.leeminjung1.domain.application.dtos.CommentListDto;
import com.leeminjung1.domain.model.comment.Comment;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import com.leeminjung1.infrastructure.repository.CommentRepository;
import com.leeminjung1.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public long countByWriterId(Long writerId) {
        return commentRepository.countByWriterId(writerId);
    }

    public Long countCommentByArticleId(Long articleId) {
        return commentRepository.countCommentByArticleId(articleId);
    }

    public void addComment(Long authorId, Long articleId, CommentDto commentDto) {
        int order = 0;
        if (commentDto.getCommentLevel() == 1) {
            order = commentRepository.findFirstByParentIdOrderByCommentOrderDesc(commentDto.getParentId())
                    .map(Comment::getCommentOrder)
                    .orElse(0) + 1;
        }

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .commentLevel(commentDto.getCommentLevel())
                .commentOrder(order)
                .parent(commentDto.getParentId() == null ? null : commentRepository.findById(commentDto.getParentId()).orElseThrow())
                .article(articleRepository.findById(articleId).orElseThrow())
                .writer(memberRepository.findById(authorId).orElseThrow())
                .voteCount(0)
                .build();

        commentRepository.save(comment);
    }

    public Page<CommentListDto> findAllCommentByMemberId(Pageable pageable, Long memberId) {
        Page<Comment> comments = commentRepository.findByWriterIdOrderByIdDesc(pageable, memberId);
        return comments.map(CommentListDto::new);
    }

    public void deleteComment(Long commentId) {
        // 지우려는 댓글이 최상위 댓글이고 자식 댓글이 남아있으면 null로 변경
        if (commentRepository.findById(commentId).get().getCommentLevel() == 0 && commentRepository.countByParentId(commentId) > 0) {
            // update set content, created_at, vote_count, file_id, writer_id null
            commentRepository.deleteCommentWhereChildrenExist(commentId);
        }
        // 지우려는 댓글이 최상위댓글이 아니지만 최상위 댓글이 null이고 다른 자식 댓글들이 없을 때 자기자신과 최상위 댓글 삭제
        else if (commentRepository.findById(commentId).get().getCommentLevel() == 1 &&
                commentRepository.findById(commentId).get().getParent().getContent()==null &&
                commentRepository.countByParentId(commentRepository.findById(commentId).get().getParent().getId()) == 1) {
            commentRepository.delete(commentRepository.findById(commentId).get().getParent());
        } else {
            commentRepository.deleteById(commentId);
        }
    }

    public void updateComment(Long commentId, String content) {
        commentRepository.updateComment(commentId, content);
    }
}
