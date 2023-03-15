package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final CategoryService categoryService;

    @GetMapping({"/", "/home"})
    public String categoryList(Model model) {
        model.addAttribute("categoryDto", categoryService.getCategoryListDto());
        return "home";
    }

}
