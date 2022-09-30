package com.leeminjung1.domain.application.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Byte commentLevel;
    private Long referenceId;
    private String content;

}
