package com.rojojun.jpainter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rojojun.jpainter.dto.MembersRequestDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Members extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(unique = true)
    @NotNull
    private String nickname;

    @NotNull
    private String password;

    // 값을 데이터 베이스에 저장하지 않도록 합니다.
    @JsonIgnore
    @Transient
    private String passwordChk;

    public Members(MembersRequestDto membersRequestDto) {
        this.nickname = getNickname();
        this.password = getPassword();
        this.passwordChk = getPasswordChk();
    }
}
