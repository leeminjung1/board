package com.leeminjung1.web.pages;

import com.leeminjung1.config.BackedLoginService;
import com.leeminjung1.domain.application.dtos.*;
import com.leeminjung1.domain.application.impl.ArticleLikeService;
import com.leeminjung1.domain.application.impl.ArticleServiceImpl;
import com.leeminjung1.domain.application.impl.CommentService;
import com.leeminjung1.domain.application.impl.MemberServiceImpl;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.comment.Comment;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.domain.model.member.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberServiceImpl memberService;
    private final BackedLoginService loginService;
    private final ArticleServiceImpl articleService;
    private final PasswordEncoder passwordEncoder;
    private final CommentService commentService;
    private final ArticleLikeService likeService;

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String login() {
        return "users/userLogin";
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "users/userRegister";
    }

    /**
     * 회원가입
     */
    @PostMapping("/register")
    public String register(@ModelAttribute("registerDto") RegisterDto registerDto, RedirectAttributes redirectAttributes) {
        memberService.registerUser(registerDto);
        redirectAttributes.addFlashAttribute("username", registerDto.getUsername());
        return "redirect:/login";
    }

    /**
     * 모든 사용자 조회
     */
    @GetMapping("/members")
    public String memberList(Model model) {
        List<Member> members = memberService.findUsers();
        model.addAttribute("members", members);
        return "users/userList";
    }

    /**
     * 사용자 활동내역
     */
    @GetMapping("/members/{memberId}")
    public String memberActivity(@PathVariable("memberId") Long memberId,
                                 String tab,
                                 Model model) {
        Member member = memberService.findById(memberId).orElseThrow();
        model.addAttribute("member", member);

        if (tab == null || tab.equals("articles")) {
            model.addAttribute("articles", articleService.findArticlesByAuthorId(memberId));
        } else if (tab.equals("commentedArticles")) {
            model.addAttribute("articles", articleService.findArticlesThatCommentedByMemberId(memberId));
        } else if (tab.equals("likedArticles")) {
            model.addAttribute("articles", articleService.findArticlesThatLikedByMemberId(memberId));
        } else if (tab.equals("comments")) {
            model.addAttribute("comments", commentService.findAllCommentByMemberId(memberId));
        }

        return "users/userActivity";
    }

    @GetMapping("/members/me")
    public String memberActivity(@AuthenticationPrincipal User user) {
        return "redirect:/members/" + memberService.getId(user.getUsername());
    }

    /**
     * 사용자 프로필 설정
     */
    @GetMapping("/settings")
    public String memberSetting(Authentication authentication, Model model) {
        UpdateMemberDto dto = memberService.newUpdateMemberDtoByUserName(authentication.getName());
        model.addAttribute("member", dto);
        return "users/userSetting";
    }

    @PostMapping("/settings")
    public String updateMember(@ModelAttribute("member") UpdateMemberDto dto) {
        memberService.updateMember(dto);
        return "redirect:/settings";
    }
}
