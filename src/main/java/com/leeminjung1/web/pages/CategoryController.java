package com.leeminjung1.web.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leeminjung1.domain.application.dtos.CategoryDto;
import com.leeminjung1.domain.application.dtos.CategoryInputDto;
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
        model.addAttribute("inputs", new CategoryInputDto());
        return "categories/updateCategory";
    }

    @PostMapping("/manage/category")
    public String manageCategory(@ModelAttribute("inputs") CategoryInputDto inputs) {
        if (inputs.getAppend() == null) {
            log.info("NO CATEGORY WAS APPENDED!!!!!");
        }
//        to fix

        return "redirect:/manage/category";
    }

    @RequestMapping("/memberInfo.do")
    public String updateCategory(@RequestParam Map<String, Object> parameters) throws JsonProcessingException {
        String json = parameters.get("paramList").toString();
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> paramList = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>(){});
        log.info(String.valueOf(paramList));
        return "redirect:/manage/category";
    }


}
