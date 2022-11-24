package com.leeminjung1.web.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
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

        List<Category> update = dto.getUpdate();
        List<CategoryVO> append = dto.getAppend();
        List<Long> delete = dto.getDelete();

        log.info(update.toString());
        log.info(append.toString());
        log.info(delete.toString());


        categoryService.deleteAllById(delete);

        List<Category> categories = new ArrayList<>();
        for(CategoryVO vo: append) {
            Category category = Category.builder()
                    .name(vo.getName())
                    .priority(vo.getPriority())
                    .parent(categoryService.findByCategoryId(vo.getParentId()))
                    .build();
            categories.add(category);
        }
        categoryService.saveCategories(categories);


        for (Category category : update) {
            category.setDepth(category.getParent().getDepth() == 0 ? 1 : 2);
        }
        categoryService.saveCategories(update);

        return "redirect:/manage/category";
    }


}
