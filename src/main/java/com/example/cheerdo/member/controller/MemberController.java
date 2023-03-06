package com.example.cheerdo.member.controller;

import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.FriendInfoResponseDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {
    private final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    @PostMapping("/info")
    @ApiOperation(notes = "회원 정보를 업데이트하는 api입니다"
            , value = "회원 image는 string타입으로 받습니다. ")
    public ResponseEntity<String> updateProfile(@RequestParam String memberId,
                                                @RequestParam String updateName,
                                                @RequestParam String updateBio,
                                                @RequestPart MultipartFile uploadImage) {
        try {
            String photoImg = null;
            if (uploadImage != null) {
                Base64.Encoder encoder = Base64.getEncoder();
                byte[] photoEncode = encoder.encode(uploadImage.getBytes());
                photoImg = new String(photoEncode, "UTF8");

            }
            logger.info("input photo image is -> {} ", photoImg);
            return new ResponseEntity<>(memberService.updateMyInfo(
                    UpdateProfileRequestDto.builder()
                            .memberId(memberId)
                            .updateBio(updateBio)
                            .uploadImage(photoImg)
                            .updateName(updateName)
                            .build()
            ), HttpStatus.OK);
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my-info")
    @ApiOperation(notes = "member의 info를 반환하는 api 입니다."
    , value = "제일 오래된 habit의 dplus day를 반환, 추후 협의 후 dplus day 처리 의논")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@RequestParam String memberId) {
        return ResponseEntity.ok().body(memberService.getMyInfo(memberId));
    }

    @GetMapping("/friend-info")
    @ApiOperation(notes = "member의 info를 반환하는 api 입니다."
            , value = "제일 오래된 habit의 dplus day를 반환, 추후 협의 후 dplus day 처리 의논")
    public ResponseEntity<FriendInfoResponseDto> getFriendInfo(@RequestParam Long relationId) {
        return ResponseEntity.ok().body(memberService.getFriendInfo(relationId));
    }
}
