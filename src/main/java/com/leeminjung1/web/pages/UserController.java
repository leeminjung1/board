package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.application.impl.MemberServiceImpl;
import com.leeminjung1.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final MemberServiceImpl userService;

    @GetMapping("/login")
    public String login() {
        return "users/userLogin";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "users/userRegister";
    }

//    @PostMapping("/login")
//    public String login(LoginDto dto) {
//
//        return "redirect:/";
//    }

    @PostMapping("/register")
    public String register(RegisterDto dto) {
        userService.register(dto);
        return "home";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = userService.findUsers();
        model.addAttribute("members", members);
        return "users/userList";
    }
}
