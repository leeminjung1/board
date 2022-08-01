package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.impl.UserServiceImpl;
import com.leeminjung1.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/user/1")
    public String list(Model model) {
        User user = userService.findById(1);
        model.addAttribute("user", user);
        return "users/userList";
    }
}
