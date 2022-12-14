package com.rojojun.jpainter.service;

import com.rojojun.jpainter.dto.LoginDto;
import com.rojojun.jpainter.dto.MembersRequestDto;
import com.rojojun.jpainter.model.Members;
import com.rojojun.jpainter.repository.MembersRepository;
import com.rojojun.jpainter.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private static String validation = "^[a-zA-Z0-9]*$";
    private final MembersRepository membersRepository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registNewMember(MembersRequestDto requestDto) {

        Members members = new Members(requestDto);
        String nickname = members.getNickname();
        String password = members.getPassword();

        // 닉네임 중복 검증
        Optional<Members> check = membersRepository.findByNickname(nickname);
        if (check.isPresent()) {
            throw new RuntimeException("중복된 ID 입니다.");
        }

        // 받은 패스워드와 패스워드 검증이 같은지 확인
        if (!Objects.equals(password, requestDto.getPasswordChk())) {
            throw new RuntimeException("비밀번호 불일치 RuntimeException");
        }
        else if (!Objects.equals(password, validation)){
            throw new RuntimeException("비밀번호의 패턴이 일치하지 않습니다.\n알파벳 및 숫자만 가능합니다.");
        }

        // 받은 패스워드를 암호화 하여 저장
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        members.setPassword(encodedPassword);
        members.setNickname(nickname);
        membersRepository.save(members);
    }

    public void loginMember(HttpServletRequest request, LoginDto loginDto) {

        // 1. LoginDto에서 유저에게 로그인 할 값을 가져온다.
        String memberName = loginDto.getNickname();
        String password = loginDto.getPassword();

        // 2. 유저가 입력한 로그인 정보들과 실제 DB에 있는 데이터를 검증하여 확인을 한다.
        Members members = membersRepository.findByNickname(memberName).orElseThrow(
                () -> new UsernameNotFoundException("회원가입 되지 않은 유저의 닉네임입니다.")
        );

        if (!bCryptPasswordEncoder.matches(password, members.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 잘못되었습니다.");
        }

        // 3. 로그인한 유저를 Authentication 객체에 관련된 정보를 저장한다 (저장되는 정보는 인증, 인가, 승인)
        Authentication authentication = new UsernamePasswordAuthenticationToken(members, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. 관련된 정보를 세션에 담아서 저장함
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }
}
