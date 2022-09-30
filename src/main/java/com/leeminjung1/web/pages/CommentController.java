package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.CommentDto;
import com.leeminjung1.domain.application.impl.ArticleServiceImpl;
import com.leeminjung1.domain.application.impl.CategoryService;
import com.leeminjung1.domain.application.impl.CommentService;
import com.leeminjung1.domain.application.impl.MemberServiceImpl;
import com.leeminjung1.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final MemberServiceImpl memberService;
    private final CategoryService categoryService;

    @PostMapping("/comment/add/{articleId}")
    public String addComment(@ModelAttribute CommentDto commentDto,
                             @PathVariable Long articleId,
                             @AuthenticationPrincipal User user) {

        Member author = memberService.findByUsername(user.getUsername());

        log.info(user.toString());
        log.info(articleId.toString());
        log.info(commentDto.toString());
        commentService.addComment(author.getId(), articleId, commentDto);

        Long categoryId = categoryService.findCategoryIdByArticleId(articleId);

        return "redirect:/" + categoryId + "/v/" + articleId;
    }
}