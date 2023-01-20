package com.example.cheerdo.login.controller;

import com.example.cheerdo.login.dto.response.error.ErrorResponseDto;
import com.example.cheerdo.login.dto.request.JoinRequestDto;
import com.example.cheerdo.login.service.MemberService;
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
    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupApi(@Valid @RequestBody JoinRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorResponseDto apiError = new ErrorResponseDto(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        if (request.isSamePassword()) {
            ErrorResponseDto apiError = new ErrorResponseDto(HttpStatus.BAD_REQUEST
                    , "입력된 비밀번호가 동일하지 않습니다.");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        if (memberService.checkUsernameDuplication(request.getMemberId())) {
            ErrorResponseDto apiError = new ErrorResponseDto(HttpStatus.BAD_REQUEST
                    , "중복된 id가 존재합니다. ");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        memberService.join(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
