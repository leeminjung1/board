package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CategoryDto {

    private List<Category> categories;
}
