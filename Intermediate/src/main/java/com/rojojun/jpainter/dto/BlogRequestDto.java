package com.rojojun.jpainter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogRequestDto {
    private String subject;
    private String writer;
    private String content;
}
