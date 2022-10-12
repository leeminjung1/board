package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryInputDto {
    List<Integer> delete;
    List<Category> append;
    List<Category> update;
}
