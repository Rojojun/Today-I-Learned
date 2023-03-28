package com.example.fastcampusmysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class PostLike {
    final private Long id;
    final private Long memeberId;
    final private Long postId;
    final private LocalDateTime createdAt;

    @Builder
    public PostLike(Long id, Long memeberId, Long postId, LocalDateTime createdAt) {
        this.id = id;
        this.memeberId = Objects.requireNonNull(memeberId);
        this.postId = Objects.requireNonNull(postId);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
