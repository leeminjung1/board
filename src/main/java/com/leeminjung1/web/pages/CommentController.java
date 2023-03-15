package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.CommentDto;
import com.leeminjung1.domain.application.service.impl.CategoryService;
import com.leeminjung1.domain.application.service.impl.CommentService;
import com.leeminjung1.domain.application.service.impl.MemberServiceImpl;
import com.leeminjung1.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final MemberServiceImpl memberService;
    private final CategoryService categoryService;

    /**
     * 댓글 작성
     */
    @PostMapping("/comment/add/{articleId}")
    public String addComment(@ModelAttribute CommentDto commentDto,
                             @PathVariable Long articleId,
                             Authentication authentication) {

        Member author = memberService.findByUsername(authentication.getName());
        commentService.addComment(author.getId(), articleId, commentDto);
        Long categoryId = categoryService.findCategoryIdByArticleId(articleId);

        return "redirect:/" + categoryId + "/v/" + articleId;
    }
}