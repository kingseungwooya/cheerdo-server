package com.example.cheerdo.login.controller;

import com.example.cheerdo.login.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.login.dto.response.error.ErrorResponseDto;
import com.example.cheerdo.login.dto.request.JoinRequestDto;
import com.example.cheerdo.login.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

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
        if (!request.isSamePassword()) {
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
        memberService.addToRoleToUser(request.getMemberId(), "ROLL_USER");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> checkMemberIdDuplicated(@RequestParam("memberId") String memberId) {
        return new ResponseEntity<>(memberService.checkUsernameDuplication(memberId), HttpStatus.OK);
    }

    @GetMapping("/user/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@RequestParam String memberId) {
        return ResponseEntity.ok().body(memberService.getInfoById(memberId));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToMember(@RequestBody RoleToMemberForm form) {
        memberService.addToRoleToUser(form.getMemberId(), form.getRoleName());
        return ResponseEntity.ok().build(); // 코드 200 반환
    }

    @PostMapping("/user/image")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile uploadImage,
                                              @RequestParam("memberId") String memberId) {
        try {
            return new ResponseEntity<>(memberService.uploadImage(uploadImage, memberId), HttpStatus.OK);
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

}

// Dto
@Data
class RoleToMemberForm {

    private String memberId;
    private String roleName;

}
