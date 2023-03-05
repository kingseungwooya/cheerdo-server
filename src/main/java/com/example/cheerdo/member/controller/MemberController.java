package com.example.cheerdo.member.controller;

import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/info")
    @ApiOperation(notes = "회원 정보를 업데이트하는 api입니다"
            , value = "회원 image는 string타입으로 받습니다. ")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequestDto updateProfileRequestDto) {
        try {
            return new ResponseEntity<>(memberService.updateMyInfo(updateProfileRequestDto), HttpStatus.OK);
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info")
    @ApiOperation(notes = "member의 info를 반환하는 api 입니다."
    , value = "제일 오래된 habit의 dplus day를 반환, 추후 협의 후 dplus day 처리 의논")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@RequestParam String memberId) {
        return ResponseEntity.ok().body(memberService.getInfoById(memberId));
    }
}
