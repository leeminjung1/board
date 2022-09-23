package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.RegisterDto;
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

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    @GetMapping({"/", "/home"})
    public String categoryList(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categories", categoryList);

        List<Integer> articlesCount = new ArrayList<>();
        for (Category category : categoryList) {
            int count = category.getArticles().size();
            articlesCount.add(count);
            log.info("count "+String.valueOf(count));
        }
        model.addAttribute("articlesCount", articlesCount);


        return "home";
    }

}
