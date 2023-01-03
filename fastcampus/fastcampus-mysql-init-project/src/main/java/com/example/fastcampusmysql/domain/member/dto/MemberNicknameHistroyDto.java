package com.example.fastcampusmysql.domain.member.dto;

import java.time.LocalDateTime;

public record MemberNicknameHistroyDto(
        Long id,
        Long memberId,
        String nickname,
        LocalDateTime createdAt
) {
}
