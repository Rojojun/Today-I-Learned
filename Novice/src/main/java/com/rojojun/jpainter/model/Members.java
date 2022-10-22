package com.rojojun.jpainter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rojojun.jpainter.dto.MembersRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Members {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(unique = true)
    private String nickname;

    private String password;

    @JsonIgnore
    @Transient
    private String passwordChk;

    public Members(MembersRequestDto membersRequestDto) {
        this.nickname = getNickname();
        this.password = getPassword();
        this.passwordChk = getPasswordChk();
    }
}
