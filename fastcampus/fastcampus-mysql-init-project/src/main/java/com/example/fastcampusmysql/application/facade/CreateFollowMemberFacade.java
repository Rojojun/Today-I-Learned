package com.example.fastcampusmysql.application.facade;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFollowMemberFacade {
    final private MemberReadService readService;
    final private FollowWriteService writeService;

    public void excute(Long fromMemberId, Long toMemberId) {
        /*
        * 1. 입력받은 memberId로 회원조회
        * 2. FollowWriteService.create()
        *
        * IMPORTANT
        * 여기(usecase, facade)에는 가능한 Logic(실제 동작하는)이 없어야함 ->
        * 도메인서비스의 흐름만 제어해야함
        * */
        var fromMember = readService.getMember(fromMemberId);
        var toMember = readService.getMember(toMemberId);

        writeService.create(fromMember, toMember);
    }
}
