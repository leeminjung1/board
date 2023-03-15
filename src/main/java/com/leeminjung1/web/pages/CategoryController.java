package com.leeminjung1.web.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leeminjung1.domain.application.dtos.CategoryRequestDto;
import com.leeminjung1.domain.application.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/manage/category")
    public String manageCategory(Model model) {
        model.addAttribute("categoryDto", categoryService.getCategoryListDto());
        model.addAttribute("requestDtos", new CategoryRequestDto());
        return "categories/updateCategory";
    }

    @PutMapping(value = "/manage/category")
    @ResponseBody
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequestDto dto) throws JsonProcessingException {
        categoryService.saveCategories(dto.getAppend());
        categoryService.deleteAllById(dto.getDelete());
        categoryService.updateCategories(dto.getUpdate());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
