package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.ArticleService;
import com.leeminjung1.domain.application.impl.ArticleServiceImpl;
import com.leeminjung1.domain.application.impl.MemberServiceImpl;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleServiceImpl articleService;
    private final MemberServiceImpl memberService;

    @GetMapping("/{categoryId}")
    public String articleListByCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        List<Article> articles = articleService.findArticlesByCategory(categoryId);
        model.addAttribute("articles", articles);
        Category category = articleService.findCategoryByCategoryId(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 입니다."));
        model.addAttribute("category", category);
        return "articles/allArticleListByCategory";
    }

    @GetMapping("/articles")
    public String allList(Model model) {
        List<Article> articles = articleService.findAllArticles();
        model.addAttribute("articles", articles);
        return "articles/allArticleList";
    }

    @GetMapping("/{categoryId}/v/{articleId}")
    public String article(@PathVariable("categoryId") Long categoryId,
                          @PathVariable("articleId") Long articleId,
                          Model model) {
        Article article = articleService.findArticleById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        model.addAttribute("article", article);
        return "articles/article";
    }

    @GetMapping("/{categoryId}/new")
    public String newArticle(@PathVariable("categoryId") Long categoryId, Model model) {
        Category category = articleService.findCategoryByCategoryId(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));
        model.addAttribute("category", category);

        model.addAttribute("article", new Article("", "", null, null));
        return "articles/newArticle";
    }

    @PostMapping("/{categoryId}/new")
    public String submitNewArticle(@ModelAttribute("article") Article article,
                                   @PathVariable("categoryId") Long categoryId,
                                   @AuthenticationPrincipal User user) {
        log.debug(user.getUsername());
        Member member = memberService.findByUsername(user.getUsername());
        Category category = articleService.findCategoryByCategoryId(categoryId).get();
        Article newArticle = new Article(article.getTitle(), article.getContent(), category, member);
        articleService.save(newArticle);
        return "redirect:/" + categoryId;
    }
}