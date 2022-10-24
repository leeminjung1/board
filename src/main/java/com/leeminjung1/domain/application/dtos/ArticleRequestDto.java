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
    private Boolean isNotice;

    @Builder
    public ArticleRequestDto(String title, String content, Category category, Member author, byte isNotice) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        this.isNotice = isNotice == 1;
    }

    public Article toEntity() {
        byte isNotice;
        if (this.isNotice) {
            isNotice = 1;
        }  else {
            isNotice = 0;
        }
        return Article.builder()
                .title(title)
                .content(content)
                .category(category)
                .author(author)
                .isNotice(isNotice)
                .createdAt(LocalDateTime.now())
                .viewCount(0)
                .likeCount(0)
                .build();
    }

}
