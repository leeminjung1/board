package com.leeminjung1.web.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    @RequestMapping("/home")
    public String hello(HttpServletResponse response) throws IOException {
        return "home";
    }
}
