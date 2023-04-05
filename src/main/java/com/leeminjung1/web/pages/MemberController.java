package com.leeminjung1.web.pages;

import com.leeminjung1.config.BackedLoginService;
import com.leeminjung1.domain.application.dtos.*;
import com.leeminjung1.domain.application.service.impl.ArticleLikeService;
import com.leeminjung1.domain.application.service.impl.ArticleService;
import com.leeminjung1.domain.application.service.impl.CommentService;
import com.leeminjung1.domain.application.service.impl.MemberServiceImpl;
import com.leeminjung1.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberServiceImpl memberService;
    private final BackedLoginService loginService;
    private final ArticleService articleService;
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

    @PostMapping("/login")
    public String loginPost() {
        return "redirect:/";
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
     *아이디/비밀번호 찾기
     */
    @GetMapping("/forgetPassword")
    public String forgot() {
        return "users/forgotPassword";
    }

    /**
     * 비밀번호 초기화
     */
    /*@PostMapping("user/resetPassword")
    public String resetPassword(HttpServletRequest request, @RequestParam("email") String email) {
        Member member = memberService.findByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException();
        }

        String token = UUID.randomUUID().toString();
        memberService.createPasswordResetTokenForUser(member, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, member));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }

*/
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
                                 Model model,
                                 Pageable pageable) {
        Member member = memberService.findById(memberId).orElseThrow();
        model.addAttribute("member", member);

        if (tab == null || tab.equals("articles")) {
            model.addAttribute("articles", articleService.findArticlesByAuthorId(pageable, memberId));
            model.addAttribute("totalArticleCount", articleService.countAllByAuthorId(memberId));
        } else if (tab.equals("commentedArticles")) {
            model.addAttribute("articles", articleService.findArticlesThatCommentedByMemberId(pageable, memberId));
            model.addAttribute("totalArticleCount", articleService.countArticlesByCommentWriterId(memberId));

        } else if (tab.equals("likedArticles")) {
            model.addAttribute("articles", articleService.findArticlesThatLikedByMemberId(pageable, memberId));
            model.addAttribute("totalArticleCount", likeService.countArticleLikeByMemberId(memberId));
        } else if (tab.equals("comments")) {
            model.addAttribute("comments", commentService.findAllCommentByMemberId(pageable, memberId));
            model.addAttribute("totalArticleCount", commentService.countByWriterId(memberId));
        }

        return "users/userActivity";
    }

    @GetMapping("/members/me")
    public String memberActivity(Authentication authentication) {
        return "redirect:/members/" + memberService.getId(authentication.getName());
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
