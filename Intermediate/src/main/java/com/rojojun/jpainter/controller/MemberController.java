package com.rojojun.jpainter.controller;

import com.rojojun.jpainter.dto.LoginDto;
import com.rojojun.jpainter.dto.MembersRequestDto;
import com.rojojun.jpainter.security.PrincipalDetails;
import com.rojojun.jpainter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입 처리
    @PostMapping("/login/signup")
    public ResponseEntity<Void> registNewMember(@Valid @RequestBody MembersRequestDto requestDto) {
        memberService.registNewMember(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        return null;
    }

    @GetMapping("/login")
    public void loginMember(HttpServletRequest request, @RequestBody LoginDto loginDto) {
        memberService.loginMember(request, loginDto);
    }
}
