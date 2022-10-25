package com.rojojun.jpainter.security;

import com.rojojun.jpainter.model.Members;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final Members members;
    public UserDetailsImpl(Members members) {
        this.members = members;
    }
    public Members getMembers() {
        return members;
    }
    // 인가를 하는 부분
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 패스워드를 받는 부분
    @Override
    public String getPassword() {
        return members.getPassword();
    }

    // 유저의 이름을 받는 부분 -> 현 프로젝트에서는 nickname임
    @Override
    public String getUsername() {
        return members.getNickname();
    }

    // 계정의 유효기간이 만료되었는지 아닌지에 대해서
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠금 상태인지 아닌지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정의 인증기간이 만료되었는가 아닌가?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화상태인지 아닌지
    @Override
    public boolean isEnabled() {
        return true;
    }
}
