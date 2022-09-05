package com.leeminjung1.domain.model.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    ARTICLE_READ("article:read"),
    ARTICLE_WRITE("article:write"),
    ARTICLE_DELETE("article:delete");

    private final String permission;

}


