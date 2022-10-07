package com.leeminjung1.web.pages;

import com.leeminjung1.domain.application.dtos.CategoryDto;
import com.leeminjung1.domain.application.impl.CategoryService;
import com.leeminjung1.domain.model.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/manage/category")
    public String manageCategory(Model model) {
        model.addAttribute("categoryDto", categoryService.getCategoryDto());
        return "categories/updateCategory";
    }

    @PutMapping("/manage/category")
    public String manageCategory(@ModelAttribute("category") Category category) {
        categoryService.updateCategory(category);

//        to fix

        return "redirect:/manage/category";

    }
}
