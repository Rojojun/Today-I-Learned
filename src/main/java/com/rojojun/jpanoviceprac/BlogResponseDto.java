package com.rojojun.jpanoviceprac;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogResponseDto {
    private int id;
    private String subject;
    private String writer;
    private String content;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.content = blog.getContent();
        this.subject = blog.getSubject();
        this.writer = blog.getWriter();
    }
}
