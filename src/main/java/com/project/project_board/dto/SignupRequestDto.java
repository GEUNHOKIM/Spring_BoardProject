package com.project.project_board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~10자리여야 합니다")
    private String username;
    @Pattern(regexp =  "^[A-Za-z0-9]{8,16}$", message = "비밀번호는 영어 대소문자와 숫자만 사용하여 8~16자리여야 합니다")
    private String password;

    private Boolean admin = false;
    private String adminToken = "";
}
