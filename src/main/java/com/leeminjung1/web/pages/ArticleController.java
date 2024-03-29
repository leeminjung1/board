package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.ArticleListDto;
import com.leeminjung1.domain.application.dtos.ArticleRequestDto;
import com.leeminjung1.domain.application.dtos.CategoryListDto;
import com.leeminjung1.domain.application.dtos.CommentDto;

import com.leeminjung1.domain.application.service.impl.*;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final MemberServiceImpl memberService;
    private final ArticleLikeService likeService;
    private final CommentService commentService;

    /**
     * 카테고리별 모든 게시글 리스트 조회
     */
    @GetMapping("/{categoryId}")
    public String articleListByCategory(@PathVariable("categoryId") Long categoryId, Model model, Pageable pageable) {
        model.addAttribute("totalArticleCount", articleService.countAllByCategoryId(categoryId));

        List<ArticleListDto> articles = articleService.findArticlesByCategory(pageable, categoryId).getContent();
        model.addAttribute("articles", articles);

        Category category = articleService.findCategoryByCategoryId(categoryId);
        model.addAttribute("category", category);

        List<ArticleListDto> noticeArticles = articleService.findNoticeArticleTop20();
        model.addAttribute("notices", noticeArticles);
        return "articles/allArticleListByCategory";
    }

    /**
     * 모든 게시글 조회
     */
    @GetMapping("/articles")
    public String allList(Model model, Pageable pageable) {
        model.addAttribute("totalArticleCount", articleService.countAll());

        List<ArticleListDto> articles = articleService.findAllArticles(pageable).getContent();
        model.addAttribute("articles", articles);

        List<ArticleListDto> noticeArticles = articleService.findNoticeArticleTop20();
        model.addAttribute("notices", noticeArticles);
        return "articles/allArticleList";
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/{categoryId}/v/{articleId}")
    public String article(@PathVariable("categoryId") Long categoryId,
                          @PathVariable("articleId") Long articleId,
                          Authentication authentication,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model) {
        Article article = articleService.findArticleById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        model.addAttribute("article", article);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("commentDto", new CommentDto());


        Member member = memberService.findByUsername(authentication.getName());
        int likeState = likeService.isMemberLikeArticle(member.getId(), articleId);
        model.addAttribute("likeState", likeState);

        // 조회수 중복 방지
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + articleId + "]")) {
                articleService.updateViewCount(articleId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + articleId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60);  /* 쿠키 시간 */
                response.addCookie(oldCookie);
            }
        } else {
            articleService.updateViewCount(articleId);
            Cookie newCookie = new Cookie("postView", request.getParameter("articleId"));
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60);
            response.addCookie(newCookie);
        }

        return "articles/article";
    }

    /**
     * 글 삭제
     */
    @PostMapping("/{categoryId}/delete/{articleId}")
    public String deleteArticle(@PathVariable("categoryId") Long categoryId,
                                @PathVariable("articleId") Long articleId) {
        articleService.deleteById(articleId);
        return "redirect:/" + categoryId;
    }

    /**
     * 글 수정
     */
    @GetMapping("/{categoryId}/update/{articleId}")
    public String updateArticle(@PathVariable("categoryId") Long categoryId,
                                @PathVariable("articleId") Long articleId,
                                Model model) {
        Article article = articleService.findArticleById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        model.addAttribute("article", new ArticleRequestDto(article));

        CategoryListDto categoryDto = categoryService.getCategoryListDto();
        model.addAttribute("categoryDto", categoryDto);
//        model.addAttribute("categoryId", categoryId);
//        model.addAttribute("articleId", articleId);
        return "articles/updateArticle";
    }

    @PostMapping("/{categoryId}/update/{articleId}")
    public String updateArticle(@ModelAttribute("article") ArticleRequestDto dto,
                                @PathVariable("categoryId") Long categoryId,
                                @PathVariable("articleId") Long articleId) {
        articleService.update(articleId, dto);
        return "redirect:/" + categoryId + "/v/" + articleId;
    }


    /**
     * 글쓰기
     */
    @GetMapping("/{categoryId}/new")
    public String newArticle(@PathVariable("categoryId") Long categoryId, Model model) {
        Category category = articleService.findCategoryByCategoryId(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("article", new ArticleRequestDto());
        return "articles/newArticle";
    }

    /**
     * 게시글 등록
     */
    @PostMapping("/{categoryId}/new")
    public String submitNewArticle(@ModelAttribute("article") ArticleRequestDto articleRequestDto,
                                   @PathVariable("categoryId") Long categoryId,
                                   Authentication authentication) {

        Long articleId = articleService.save(authentication.getName(), articleRequestDto);
        return "redirect:/" + categoryId + "/v/" + articleId;
    }

    /**
     * 유저활동에서 선택된 게시글 삭제
     */
    @PostMapping("api/delete/selectedArticles")
    public ResponseEntity<?> deleteSelectedArticles(HttpServletRequest request) {
        String[] selectedValues = request.getParameterValues("selectedValues");
        List<Long> ids = new ArrayList<>();
        for (String selectedValue : selectedValues) {
            ids.add(Long.parseLong(selectedValue));
        }
        articleService.deleteByIdIn(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 좋아요
     */
    @PostMapping("/api/like/article/{articleId}")
    public ResponseEntity<?> likeIt(@PathVariable Long articleId, Authentication authentication) {
        likeService.likeArticle(articleId, memberService.getId(authentication.getName()));
        articleService.updateLikeCount(articleId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 좋아요 취소
     */
    @DeleteMapping("/api/like/article/{articleId}")
    public ResponseEntity<?> unlikeIt(@PathVariable Long articleId, Authentication authentication) {
        likeService.unlikeArticle(articleId, memberService.getId(authentication.getName()));
        articleService.updateLikeCount(articleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("api/delete/comment")
    public ResponseEntity<?> deleteComment(HttpServletRequest request) {
        Long commentId = Long.valueOf(request.getParameter("commentId"));
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 수정
     */
    @PostMapping("api/update/comment")
    public ResponseEntity<?> updateComment(HttpServletRequest request) {
        Long commentId = Long.valueOf(request.getParameter("commentId"));
        String content = request.getParameter("content");
        commentService.updateComment(commentId, content);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}