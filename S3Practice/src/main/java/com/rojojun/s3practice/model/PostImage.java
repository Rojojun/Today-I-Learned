package com.rojojun.s3practice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
