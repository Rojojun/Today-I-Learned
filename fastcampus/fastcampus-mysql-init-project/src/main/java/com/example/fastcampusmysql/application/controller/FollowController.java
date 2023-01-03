package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.facade.CreateFollowMemberFacade;
import com.example.fastcampusmysql.application.facade.GetFollowMemberFacade;
import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {
    private final CreateFollowMemberFacade createFollowMemberFacade;
    private final GetFollowMemberFacade getFollowMemberFacade;

    @PostMapping("/{fromId}/{toId}")
    public void regist(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowMemberFacade.excute(fromId, toId);
    }

    @GetMapping("/members/{fromId}")
    public List<MemberDto> read(@PathVariable Long fromId) {
        return getFollowMemberFacade.execute(fromId);
    }
}
