package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.impl.ArticleServiceImpl;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleServiceImpl articleService;

    @GetMapping("/articles")
    public String list(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "articles/articleList";
    }
}