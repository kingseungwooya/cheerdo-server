package com.example.cheerdo.login.controller;

import com.example.cheerdo.entity.Role;
import com.example.cheerdo.entity.enums.RoleName;
import com.example.cheerdo.login.dto.response.error.ErrorResponseDto;
import com.example.cheerdo.login.dto.request.JoinRequestDto;
import com.example.cheerdo.login.service.LoginService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupApi(@Valid @RequestBody JoinRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorResponseDto apiError = new ErrorResponseDto(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        if (!request.isSamePassword()) {
            ErrorResponseDto apiError = new ErrorResponseDto(HttpStatus.BAD_REQUEST
                    , "입력된 비밀번호가 동일하지 않습니다.");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        if (loginService.checkUsernameDuplication(request.getMemberId())) {
            ErrorResponseDto apiError = new ErrorResponseDto(HttpStatus.BAD_REQUEST
                    , "중복된 id가 존재합니다. ");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        loginService.join(request);
        loginService.addToRoleToUser(request.getMemberId(), RoleName.ROLE_USER);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signup/check-id")
    public ResponseEntity<Boolean> checkMemberIdDuplicated(@RequestParam("memberId") String memberId) {
        return new ResponseEntity<>(loginService.checkUsernameDuplication(memberId), HttpStatus.OK);
    }

    /*
    @PostMapping("/role/add-to-user")
    public ResponseEntity<?> addRoleToMember(@RequestBody RoleToMemberForm form) {
        loginService.addToRoleToUser(form.getMemberId(), form.getRoleName());
        return ResponseEntity.ok().build(); // 코드 200 반환
    }*/

}

// Dto
@Data
class RoleToMemberForm {

    private String memberId;
    private String roleName;

}
