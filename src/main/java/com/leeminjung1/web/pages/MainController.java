package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.RegisterDto;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CategoryRepository categoryRepository;

    @GetMapping({"/", "/home"})
    public String categoryList(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categories", categoryList);
        return "home";
    }

}
