package com.example.fastcampusmysql.domain.member.dto;

import java.time.LocalDate;

public record RegisterMemberCommand(
        /*
        JDK 14 부터 free -> 16 부터 정식 기능
        Getter / Setter를 자동으로 불러서 Property 형식으로 사용 할 수 있음.
        단순히 데이터만 담고 있는 클래스에 자주 사용됨
        */
        String email,
        String nickname,
        LocalDate birthday
) {
}
