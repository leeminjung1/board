package com.leeminjung1.web.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leeminjung1.domain.application.vo.CategoryVO;
import com.leeminjung1.domain.application.dtos.CategoryRequestDto;
import com.leeminjung1.domain.application.impl.CategoryService;
import com.leeminjung1.domain.model.category.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/manage/category")
    public String manageCategory(Model model) {
        model.addAttribute("categoryDto", categoryService.getCategoryDto());
        model.addAttribute("requestDtos", new CategoryRequestDto());
        return "categories/updateCategory";
    }

   // @PostMapping("/manage/category")
    public String manageCategory(@ModelAttribute("requestDtos") CategoryRequestDto requestDtos) {
        if (requestDtos.getAppend() == null) {
            log.info("NO CATEGORY WAS APPENDED!!!!!");
        }
//        to fix

        return "redirect:/manage/category";
    }

    @RequestMapping(value = "/manage/category", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public String updateCategory(@RequestBody CategoryRequestDto dto) throws JsonProcessingException {
        log.info(dto.toString());

        List<CategoryVO> update = dto.getUpdate();
        List<CategoryVO> append = dto.getAppend();
        List<Long> delete = dto.getDelete();

        List<Category> categories = new ArrayList<>();
        for(CategoryVO vo: append) {
            Category category = Category.builder()
                    .name(vo.getName())
                    .priority(vo.getPriority())
                    .parent(categoryService.findByCategoryId(vo.getParentId()))
                    .build();
            categories.add(category);
        }
        categoryService.addCategoryByList(categories);

        categoryService.deleteAllById(delete);

        return "redirect:/manage/category";
    }


}
