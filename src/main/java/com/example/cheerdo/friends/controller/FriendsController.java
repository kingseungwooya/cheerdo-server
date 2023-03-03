package com.example.cheerdo.friends.controller;

import com.example.cheerdo.friends.dto.request.RemoveOrAcceptRequestDto;
import com.example.cheerdo.friends.dto.request.SendPostRequestDto;
import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.GetFriendRequestResponseDto;
import com.example.cheerdo.friends.dto.response.GetReceivedPostRequestResponseDto;
import com.example.cheerdo.friends.dto.response.GetFriendResponseDto;
import com.example.cheerdo.friends.service.FriendRelationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friend")
@RequiredArgsConstructor
public class FriendsController {
    private final Logger logger = LoggerFactory.getLogger(FriendsController.class);
    private final FriendRelationService friendRelationService;
    @GetMapping(value = "/list/{userId}")
    @ApiOperation(value = "userId의 초기 Friend 화면에 필요한 data를 가져오는 api"
            , notes = "반환값으로 relationId memberId name list가 반환된다")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getMyFriendList(@PathVariable("userId") String userId) {
        logger.info("request : UserID -> {}", userId);
        try {
            List<GetFriendResponseDto> getFriendResponseDtos = friendRelationService.getMyFriendList(userId);
            return new ResponseEntity<>(getFriendResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "보낸 친구요청을 가져오는 API"
            , notes = "반환값으로 relationId name list가 반환된다")
    @GetMapping(value = "/getrequest/{userId}")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getMyRequest(@PathVariable("userId") String userId) {
        logger.info("request is -> {}", userId);
        try {
            List<GetFriendRequestResponseDto> getFriendRequestResponseDtos = friendRelationService.getMyRequest(userId);
            return new ResponseEntity<>(getFriendRequestResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "친구 요청을 보내는 api"
            , notes = "반환값으로 Http status가 반환된다.")
    @PostMapping(value = "/sendrequest")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> sendRequest(@RequestBody SendRequestDto sendRequestDto) {
        logger.info("request is -> {}", sendRequestDto.toString());
        try {
            friendRelationService.sendRequest(sendRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "받은 친구요청을 가져오는 API"
            , notes = "반환값으로 relationId memberId name list가 반환된다")
    @GetMapping(value = "/receivedrequest/{userId}")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getReceivedRequest(@PathVariable("userId") String userId) {
        logger.info("request is -> {}", userId);
        try {
            List<GetFriendResponseDto> getFriendResponseDtos = friendRelationService.getReceivedRequest(userId);
            return new ResponseEntity<>(getFriendResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "친구 요청을 받거나 삭제하는 api"
            , notes = "받은 요청을 수락 또는 거절할때, 보낸 요청을 삭제할때 사용이 가능하다. 반환값으로 Http status가 반환된다.")
    @PostMapping(value = "/removeoracceptrequest")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> removeOrAcceptRequest(@RequestBody RemoveOrAcceptRequestDto removeOrAcceptRequestDto) {
        logger.info("request is -> {}", removeOrAcceptRequestDto.getRelationId());
        try {
            friendRelationService.removeOrAcceptRequest(removeOrAcceptRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "친구에게 편지요청하는 API"
            , notes = "relationId를 받아 relation에 해당하는 인원에게 PostRequest를 생성. 반환값으로 Http status가 반환된다.")
    @PostMapping(value = "/sendpostrequest")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> sendPostRequest(@RequestBody SendPostRequestDto sendPostRequestDto) {
        logger.info("request is -> {}", sendPostRequestDto.getRelationId());
        try {
            friendRelationService.sendPostRequest(sendPostRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "받은 편지요청을 가져오는 API"
            , notes = "userId를 받고 반환값으로 sendDateTime과 friendId friendName list가 반환된다")
    @GetMapping(value = "/receivedpostrequest/{userId}")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getReceivedPostRequest(@PathVariable("userId") String userId) {
        logger.info("request is -> {}", userId);
        try {
            List<GetReceivedPostRequestResponseDto> getReceivedPostRequestResponseDtos = friendRelationService.getReceivedPostRequest(userId);
            return new ResponseEntity<>(getReceivedPostRequestResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 편지를 요청하는 api
}
