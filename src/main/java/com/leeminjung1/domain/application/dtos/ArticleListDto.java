package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.infrastructure.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleListDto {
    private Long id;
    private String title;
    private Category category;
    private Member author;
    private String createdAt;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;

    public ArticleListDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.category = article.getCategory();
        this.author = article.getAuthor();
        this.createdAt = DateUtil.getDiffTime(article.getCreatedAt());
        this.viewCount = article.getViewCount();
        this.likeCount = article.getLikeCount();
        this.commentCount = article.getComments().size();
    }

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .category(category)
                .author(author)
                .build();
    }
}
