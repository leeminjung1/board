package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.RegisterDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    @RequestMapping({"/", "/home"})
    public String hello() {
        return "home";
    }
}
