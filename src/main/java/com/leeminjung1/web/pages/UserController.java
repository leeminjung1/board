package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.application.impl.UserServiceImpl;
import com.leeminjung1.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

//    @PostMapping("/login")
//    public String login(Logindto dto) {
//        userService.register(dto);
//        return "redirect:/";
//    }

    @PostMapping("/register")
    public String register(RegisterDto dto) {
        userService.register(dto);

        return "redirect:/";
    }


    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }
}
