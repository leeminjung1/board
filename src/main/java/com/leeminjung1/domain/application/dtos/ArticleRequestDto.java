package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ArticleRequestDto {

    private String title;
    private String content;
    private Category category;
    private Member author;

    @Builder
    public ArticleRequestDto(String title, String content, Category category, Member author) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
    }

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .category(category)
                .author(author)
                .createdAt(LocalDateTime.now())
                .viewCount(0)
                .voteCount(0)
                .build();
    }

}
