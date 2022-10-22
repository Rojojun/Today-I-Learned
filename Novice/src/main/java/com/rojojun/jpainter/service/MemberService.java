package com.rojojun.jpainter.service;

import com.rojojun.jpainter.dto.MembersRequestDto;
import com.rojojun.jpainter.model.Members;
import com.rojojun.jpainter.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MembersRepository membersRepository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void registNewMember(MembersRequestDto requestDto) {

        Members members = new Members(requestDto);

        // 받은 패스워드와 패스워드 검증이 같은지 확인
        if (Objects.equals(requestDto.getPassword(), requestDto.getPasswordChk())) {
            // 받은 패스워드를 암호화 하여 저장
            String encodedPassword = bCryptPasswordEncoder.encode(requestDto.getPassword());
            members.setPassword(encodedPassword);
            members.setNickname(requestDto.getNickname());
            membersRepository.save(members);
        }
        else {
            throw new RuntimeException("비밀번호 불일치 RuntimeException");
        }
    }
}
