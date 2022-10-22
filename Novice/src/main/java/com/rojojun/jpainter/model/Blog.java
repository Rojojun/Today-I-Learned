package com.rojojun.jpainter.model;

import com.rojojun.jpainter.dto.BlogRequest4PutDto;
import com.rojojun.jpainter.dto.BlogRequestDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Blog {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @Column(length = 20)
    @NotNull
    private String subject;

    @Column(unique = true, length = 10)
    @NotNull
    private String writer;

    @NotNull
    private String content;

    // Only use for SERVICE
    public Blog(BlogRequestDto blogRequestDto) {
        this.subject = blogRequestDto.getSubject();
        this.content = blogRequestDto.getSubject();
        this.writer = blogRequestDto.getWriter();
    }

    // Only use for edit but it could error
    public void updatePostError(BlogRequestDto blogRequestDto) {
        this.subject = blogRequestDto.getSubject();
        this.content = blogRequestDto.getContent();
    }

    public void updatePost(BlogRequest4PutDto blogRequestDto) {
        this.subject = blogRequestDto.getSubject();
        this.content = blogRequestDto.getContent();
    }
}
