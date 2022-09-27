package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.CategoryDto;
import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.application.impl.CategoryService;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import com.leeminjung1.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final CategoryService categoryService;

    @GetMapping({"/", "/home"})
    public String categoryList(Model model) {
        model.addAttribute("categoryDto", categoryService.getCategoryDto());
        return "home";
    }

}
