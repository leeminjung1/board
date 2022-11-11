package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.comment.Comment;
import com.leeminjung1.infrastructure.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentListDto {
    private Long articleId;
    private Long categoryId;
    private String content;
    private String title;
    private String createdAt;
    private Long commentCount;

    public CommentListDto(Comment comment) {
        this.articleId = comment.getArticle().getId();
        this.categoryId = comment.getArticle().getCategory().getId();
        this.content = comment.getContent();
        this.title = comment.getArticle().getTitle();
        this.createdAt = DateUtil.getDateTimeToString(comment.getArticle().getCreatedAt());
        this.commentCount = comment.getArticle().getCommentCount();
    }
}
