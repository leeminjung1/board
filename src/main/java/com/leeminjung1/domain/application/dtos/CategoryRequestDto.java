package com.leeminjung1.domain.application.dtos;

import com.leeminjung1.domain.application.vo.CategoryVO;
import com.leeminjung1.domain.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryRequestDto {
    List<Long> delete;
    List<CategoryVO> append;
    List<Category> update;

}
