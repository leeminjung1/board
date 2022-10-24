package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.dtos.ArticleListDto;
import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    CategoryService categoryService;

    @Test
    public void newArticle() throws Exception {
        // given
        Member user = memberService.findByUsername("admin01");
        Category category = categoryService.findByCategoryName("category0");

        Article article = Article.builder()
                .likeCount(0)
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .author(user)
                .content("content")
                .category(category)
                .title("title")
                .isNotice((byte)1)
                .build();
        // when
        Long articleId = articleService.save(article);

        // then
        assertEquals(article, articleService.findArticleById(articleId).get());
    }

    @Test
    public void getNotice() throws Exception {
        // given
        Member user = memberService.findByUsername("admin01");
        Category category = categoryService.findByCategoryName("category0");

        Article article = Article.builder()
                .createdAt(LocalDateTime.now())
                .author(user)
                .content("content")
                .category(category)
                .title("title")
                .isNotice((byte)1)
                .build();
        // when
        Long articleId = articleService.save(article);
        List<Article> notices = articleService.findNoticeArticles();
        // then
        assertEquals(article, notices.get(0));
    }
}