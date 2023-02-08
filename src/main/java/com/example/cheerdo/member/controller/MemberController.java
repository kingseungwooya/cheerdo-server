package com.example.cheerdo.member.controller;

import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.member.service.MemberService;
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
    public ResponseEntity<String> uploadImage(@RequestBody UpdateProfileRequestDto updateProfileRequestDto) {
        try {
            return new ResponseEntity<>(memberService.updateMyInfo(updateProfileRequestDto), HttpStatus.OK);
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@RequestParam String memberId) {
        return ResponseEntity.ok().body(memberService.getInfoById(memberId));
    }
}
