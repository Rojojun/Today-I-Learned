package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class MemberNicknameHistory {
    /* History성 Data는 정규화의 대상이 아님
    * ex) E-commerce -> 사업장의 이름이 변경될 경우 or 사업 아이템의 이름이 변경될 경우
    * 정규화를 해야할까? -> 의논을 통해서 결정하는게 BEST
     */
    final private Long id;
    final private String nickname;
    final private Long memberId;
    final private LocalDateTime createdAt;

    @Builder
    public MemberNicknameHistory(Long id, String nickname, Long memberId, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = Objects.requireNonNull(nickname);
        this.memberId = Objects.requireNonNull(memberId);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

}
