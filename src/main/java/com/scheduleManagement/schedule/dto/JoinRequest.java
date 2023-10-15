package com.scheduleManagement.schedule.dto;


import com.scheduleManagement.schedule.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String loginId;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "이름이 비어있습니다.")
    private String name;
    @NotBlank(message = "닉네임이 비어있습니다.")
    private String nickname;

    // 비밀번호 암호화 X
    public User toEntity() {
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .nickname(this.nickname)
                .role(UserRole.USER)
                .build();
    }

    // 비밀번호 암호화
    public User toEntity(String encodedPassword) {
        System.out.println("encodedPassword = " + encodedPassword);
        return User.builder()
                .loginId(this.loginId)
                .password(encodedPassword)
                .name(this.name)
                .nickname(this.nickname)
                .role(UserRole.USER)
                .build();
    }
}