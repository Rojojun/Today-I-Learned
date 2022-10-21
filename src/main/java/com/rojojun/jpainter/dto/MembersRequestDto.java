package com.rojojun.jpainter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MembersRequestDto {
    private String nickname;
    private String password;
    private String passwordChk;
}
