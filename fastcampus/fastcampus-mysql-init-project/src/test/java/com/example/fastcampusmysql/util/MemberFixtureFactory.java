package com.example.fastcampusmysql.util;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {
    // default seed
    static public Member create() {
        var param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Member.class);
    }
    // ObjectMother Pattern 으로 만든 Member
    // Seed가 같으면 Random으로 생성되는 난수값이 다 같음 = Random과 같은 원리
    // Return 값이 EasyRandom이므로 체이닝 된 seed사용 OK
    static public Member create(Long seed) {
        var param = new EasyRandomParameters().seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }
}
