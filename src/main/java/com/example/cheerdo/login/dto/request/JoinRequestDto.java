package com.example.cheerdo.login.dto.request;


import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Role;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.Id;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDto {

    private static final int DEFAULT_COIN_COUNT = 10;
    private static final int DEFAULT_HABIT_PROGRESS = 0;

    @Size(min = 8, max = 12, message = "아이디는 8~12 글자 사이로 만들어주세요. ")
    @NotEmpty(message = "아이디는 필수 입력 값입니다.")
    private String memberId;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String passwordConfirm;

    @NotEmpty(message = "이름 입력은 필수 입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자를 제외한 2~10자리여야 합니다.")
    private String name;

    private String bio;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public Boolean isSamePassword() {
        if (this.password.equals(this.passwordConfirm)) {
            return true;
        }
        return false;
    }

    public Member dtoToMember(Role role) {
        return Member.builder()
                .id(memberId)
                .password(password)
                .bio(bio)
                .name(name)
                .coinCount(DEFAULT_COIN_COUNT)
                .habitProgress(DEFAULT_HABIT_PROGRESS)
                .roles(List.of(role))
                .build();
    }

}
