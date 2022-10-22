package com.rojojun.jpainter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogRequest4PutDto {
    private String subject;
    private String content;
}
