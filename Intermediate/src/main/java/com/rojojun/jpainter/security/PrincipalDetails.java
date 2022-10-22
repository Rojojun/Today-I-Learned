package com.rojojun.jpainter.security;

import com.rojojun.jpainter.model.Members;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private Members members;

    public PrincipalDetails(Members members) {
        this.members = members;
    }

    // Members의 유저 인가 정보를 받아오는 부분 -> 현재 프로젝트에서는 인가 부분이 없기에 null을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return null;
            }
        });
        return null;
    }

    @Override
    public String getPassword() {
        return members.getPassword();
    }

    // Members객체 안에 있는 유저의 이름을 반환하는 메소드 -> Members 객체 내부에는 username변수를 nickname으로 받기에 getNickname으로 꺼내줌
    @Override
    public String getUsername() {
        return members.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
