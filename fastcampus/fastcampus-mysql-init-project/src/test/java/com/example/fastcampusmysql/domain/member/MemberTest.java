package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MemberTest {
    // test 코드는 if 혹은 분기 위주로
    @DisplayName("회원은 닉네임을 변경 할 수 있다.")
    @Test
    public void testChangeName() {
        /*// objectMother Pattern
        LongStream.range(0, 10)
                .mapToObj(MemberFixtureFactory::create)
                         //BEFORE LAMBDA : i -> MemberFixtureFactory.create(i)
                .forEach(member -> {
                    System.out.println(member.getNickname());
                });*/
        var member = MemberFixtureFactory.create();
        var expected = "CHANGED";
        member.changeNickname(expected);

        Assertions.assertEquals(expected, member.getNickname());
    }

    @DisplayName("회원의 닉네임은 10글자를 초과 할 수 없다.")
    @Test
    public void testNicknameMaxLength() {
        var member = MemberFixtureFactory.create();
        var overTenWords = "CHANGEDCHANGED";
        member.changeNickname(overTenWords);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            member.changeNickname(overTenWords);
        });
    }
}
