package com.rojojun.s3practice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * AWS의 키값 및 URL을 저장하기 위한 객체
 * Entity가 아닌 DTO형태로 지정하여, Key 값 및 Path는 DB에 저장하여 정규화 분리 진행
 * */

@Getter
@Entity
@NoArgsConstructor
public class PostImage {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String key;

    public PostImage(String url, String key) {
        this.url = url;
        this.key = key;
    }
}
