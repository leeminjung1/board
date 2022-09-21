package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.ArticleListDto;
import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.application.dtos.UpdateMemberDto;
import com.leeminjung1.domain.application.impl.ArticleServiceImpl;
import com.leeminjung1.domain.application.impl.MemberServiceImpl;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.domain.model.member.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberServiceImpl memberService;
    private final ArticleServiceImpl articleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register() {
        memberService.registerUser(new RegisterDto("user01", "test@test.com", "pass"));
        memberService.registerAdmin(new RegisterDto("admin02", "test2@test.com", "pass"));

        return "redirect:/";
    }


    @GetMapping("/login")
    public String login() {
        return "users/userLogin";
    }

//    @GetMapping("/register")
//    public String register(Model model) {
//        model.addAttribute("registerDto", new RegisterDto());
//        return "users/userRegister";
//    }

/*    @PostMapping("/login")
    public String login(LoginDto dto) {
        return "home";
    }*/

//    @PostMapping("/register")
//    public String register(RegisterDto dto) {
//        userService.register(dto);
//        return "/";
//    }

    @GetMapping("/members")
    public String memberList(Model model) {
        List<Member> members = memberService.findUsers();
        model.addAttribute("members", members);
        return "users/userList";
    }

    @GetMapping("/members/{memberId}")
    public String memberActivity(@PathVariable("memberId") Long memberId,
                                 Model model) {
        Member member = memberService.findById(memberId).orElseThrow();
        model.addAttribute("member", member);

        List<ArticleListDto> articles = articleService.findArticlesByAuthorId(memberId);
        model.addAttribute("articles", articles);
        return "users/userActivity";
    }

    @GetMapping("/members/me")
    public String memberActivity(@AuthenticationPrincipal User user, Model model) {
        Member member = memberService.findByUsername(user.getUsername());
        model.addAttribute("member", member);

        List<ArticleListDto> articles = articleService.findArticlesByAuthorId(member.getId());
        model.addAttribute("articles", articles);
        return "redirect:/members/" + member.getId();
    }

    @GetMapping("/settings")
    public String memberSetting(@AuthenticationPrincipal User user, Model model) {
        UpdateMemberDto dto = memberService.findMemberDtoForUpdate(user.getUsername());
        model.addAttribute("member", dto);
        log.info(String.valueOf(dto.getId()));
        return "users/userSetting";
    }

    @PostMapping("/settings")
    public String updateMember(@AuthenticationPrincipal User user,
                               @ModelAttribute("member") UpdateMemberDto dto) {
        memberService.updateMember(user.getUsername(), dto);

        return "users/userSetting";
    }
}
