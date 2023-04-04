package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleRequestDto {

    private Long id;
    private String title;
    private String content;
    private Long categoryId;
    private String categoryName;
    private Long authorId;
    private Boolean isNotice;

    @Builder
    public ArticleRequestDto(String title, String content, Long categoryId, String categoryName, Long authorId, byte isNotice) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.authorId = authorId;
        this.isNotice = isNotice == 1;
    }

    public ArticleRequestDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.categoryId = article.getCategory().getId();
        this.categoryName = article.getCategory().getName();
        this.authorId = article.getAuthor().getId();
        this.isNotice = article.getIsNotice() == 1;
    }
}
