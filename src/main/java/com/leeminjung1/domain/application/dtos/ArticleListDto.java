package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.infrastructure.repository.CommentRepository;
import com.leeminjung1.infrastructure.utils.DateUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleListDto {
    private Long id;
    private String title;
    private Long categoryId;
    private String categoryName;
    private Long authorId;
    private String authorName;
    private String createdAt;
    private Integer viewCount;
    private Integer likeCount;
    private Long commentCount;

    public ArticleListDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.categoryId = article.getCategory().getId();
        this.categoryName = article.getCategory().getName();
        this.authorId = article.getAuthor().getId();
        this.authorName = article.getAuthor().getUsername();
        this.createdAt = DateUtil.getDiffTime(article.getCreatedAt());
        this.viewCount = article.getViewCount();
        this.likeCount = article.getLikeCount();
        this.commentCount = article.getCommentCount();
    }

}
