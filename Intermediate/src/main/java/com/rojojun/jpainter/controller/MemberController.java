package com.rojojun.jpainter.controller;

import com.rojojun.jpainter.dto.MembersRequestDto;
import com.rojojun.jpainter.security.PrincipalDetails;
import com.rojojun.jpainter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login/signup")
    public ResponseEntity<Void> registNewMember(@RequestBody MembersRequestDto requestDto) {
        memberService.registNewMember(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public void loginMember(@AuthenticationPrincipal PrincipalDetails principalDetails) {

    }
}
