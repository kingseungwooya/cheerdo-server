package com.example.cheerdo.friends.controller;

import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.SentFollowResponseDto;
import com.example.cheerdo.friends.dto.response.FollowerResponseDto;
import com.example.cheerdo.friends.service.FollowService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @ApiOperation(value = "보낸 친구요청을 가져오는 기능"
            , notes = "반환값으로 relationId 반환된다")
    @GetMapping(value = "/sent-follow")
    public ResponseEntity<List<SentFollowResponseDto>> getSentRequest(@RequestParam("memberId") String memberId) {
        return new ResponseEntity<>(followService.getSentFollow(memberId), HttpStatus.OK);
    }

    @ApiOperation(value = "친구 요청을 보내는 기능"
            , notes = "이미 친구가 되어 있는 친구는 안뜨게 하기 ")
    @PostMapping(value = "/follow")
    public ResponseEntity<Void> follow(@RequestBody SendRequestDto sendRequestDto) {
        followService.sendFollow(sendRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "받은 친구요청을 가져오는 기능")
    @GetMapping(value = "/received-follow")
    public ResponseEntity<List<FollowerResponseDto>> getReceivedRequest(@RequestParam String memberId) {
        return new ResponseEntity<>(followService.getFollowRequest(memberId), HttpStatus.OK);
    }

    @ApiOperation(value = "친구 요청을 거절하는 기능")
    @PostMapping(value = "/refuse")
    public ResponseEntity<Void> requestRefuse(@RequestParam Long relationId) {
        followService.refuse(relationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "친구 요청을 수락하는기능")
    @PostMapping(value = "/accept")
    public ResponseEntity<Void> requestAccept(@RequestParam Long relationId) {
        followService.accept(relationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
