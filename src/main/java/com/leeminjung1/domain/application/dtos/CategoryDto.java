package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryDto {

    private Category category;
    private Integer articleCount;
}
