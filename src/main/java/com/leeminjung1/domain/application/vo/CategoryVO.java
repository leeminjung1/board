package com.leeminjung1.domain.application.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryVO {
    private Long id;
    private String name;
    private Integer priority;
    private Long parentId;
}
