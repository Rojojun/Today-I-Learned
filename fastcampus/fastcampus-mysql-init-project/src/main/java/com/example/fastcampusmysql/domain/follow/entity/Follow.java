package com.example.fastcampusmysql.domain.follow.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Follow {
    /*
    Follow 데이터의 성격
    데이터의 최신성을 보장해줘야하는 테이블이기 때문에, 정규화를 해야함.
    -> 데이터 중복을 줄여야함 = 데이터의 최신성을 높여주는 작업
    WHY? 닉네임 변경은 자주 있는 일이기 때문에, 관리의 편의성을 위해서 정규화를 하는 것
    정규화를 할 때 고민해야할 사항
    닉네임의 최신성 데이터를 어떻게 가져와야할까?
    1. JOIN
    2. JOIN 없이 Query로
    3. 별도 데이터베이스를 이용
    4. 기타 방법
    ======> JOIN을 대중적으로 많이 생각함 "BUT" JOIN은 미루는게 좋음
    [!WHY???]
    Follow -> Member Join
    Follow가 Member의 Repository 영역까지 침투를 하게 됨
    ===> 강한 결합 : 유연성있는 아키텍쳐와 시스템에서는 상당히 비효율적
    ===> 아키텍쳐적으로 성능 문제를 풀거나, 리팩토링을 할 때 힘들어짐
    ===> 초기 설계는 결합도를 낮추는게 확장성에 유리함
    ===> JOIN은 Basic SELECT 쿼리보다 성능 이슈를 발행
    ===> JOIN을 사용함으로서 캐시같은 조회용 DB를 활용할 기회를 줄임
     */
    final private Long id;
    final private Long fromMemberId;
    final private Long toMemberId;
    final private LocalDateTime createdAt;
    
    // Builder pattern의 동작원리 공부
    @Builder
    public Follow(Long id, Long fromMemberId, Long toMemberId, LocalDateTime createdAt) {
        this.id = id;
        this.fromMemberId = Objects.requireNonNull(fromMemberId);
        this.toMemberId = Objects.requireNonNull(toMemberId);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
