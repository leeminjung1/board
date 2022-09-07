package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.application.impl.MemberServiceImpl;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.domain.model.member.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final MemberServiceImpl memberService;
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
    public String list(Model model) {
        List<Member> members = memberService.findUsers();
        model.addAttribute("members", members);
        return "users/userList";
    }
}
