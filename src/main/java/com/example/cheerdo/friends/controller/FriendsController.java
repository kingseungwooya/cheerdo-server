package com.example.cheerdo.friends.controller;

import com.example.cheerdo.friends.dto.request.RemoveOrAcceptRequestDto;
import com.example.cheerdo.friends.dto.request.SendPostRequestDto;
import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.GetFriendRequestResponseDto;
import com.example.cheerdo.friends.dto.response.GetReceivedPostRequestResponseDto;
import com.example.cheerdo.friends.dto.response.GetFriendResponseDto;
import com.example.cheerdo.friends.dto.response.GetSearchedFriendResponseDto;
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
@RequestMapping("/api/member/friend")
@RequiredArgsConstructor
public class FriendsController {
    private final Logger logger = LoggerFactory.getLogger(FriendsController.class);
    private final FriendRelationService friendRelationService;
    @GetMapping(value = "/list/{memberId}")
    @ApiOperation(value = "memberId의 초기 Friend 화면에 필요한 data를 가져오는 api"
            , notes = "반환값으로 relationId memberId name list가 반환된다")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getMyFriendList(@PathVariable("memberId") String memberId) {
        logger.info("request : memberId -> {}", memberId);
        try {
            List<GetFriendResponseDto> getFriendResponseDtos = friendRelationService.getMyFriendList(memberId);
            return new ResponseEntity<>(getFriendResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "보낸 친구요청을 가져오는 API"
            , notes = "반환값으로 relationId name list가 반환된다")
    @GetMapping(value = "/requests/{memberId}/send")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getMyRequest(@PathVariable("memberId") String memberId) {
        logger.info("request is -> {}", memberId);
        try {
            List<GetFriendRequestResponseDto> getFriendRequestResponseDtos = friendRelationService.getMyRequest(memberId);
            return new ResponseEntity<>(getFriendRequestResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "친구 요청을 보내는 api"
            , notes = "반환값으로 Http status가 반환된다.")
    @PostMapping(value = "/requests")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> sendRequest(@RequestBody SendRequestDto sendRequestDto) {
        logger.info("request is -> {}", sendRequestDto.toString());
        try {
            if(sendRequestDto.getFriendId().equals(sendRequestDto.getMemberId())) {
                throw new Exception("friend and member are the same");
            }
            friendRelationService.sendRequest(sendRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "받은 친구요청을 가져오는 API"
            , notes = "반환값으로 relationId memberId name list가 반환된다")
    @GetMapping(value = "/requests/{memberId}/receive")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getReceivedRequest(@PathVariable("memberId") String memberId) {
        logger.info("request is -> {}", memberId);
        try {
            List<GetFriendResponseDto> getFriendResponseDtos = friendRelationService.getReceivedRequest(memberId);
            return new ResponseEntity<>(getFriendResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "친구 요청을 받거나 삭제하는 api"
            , notes = "받은 요청을 수락 또는 거절할때, 보낸 요청을 삭제할때 사용이 가능하다. 반환값으로 Http status가 반환된다.")
    @PostMapping(value = "/requests/control")
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

    @DeleteMapping(value = "/relation/{relationId}")
    @ApiOperation(value = "친구를 삭제하는 api"
            , notes = "쌍방으로 수락된 친구 relation을 삭제하는 api. http status가 return된다.")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> removeFriendRelation(@PathVariable("relationId") Long relationId) {
        logger.info("request : relationId -> {}", relationId);
        try {
            friendRelationService.deleteRelation(relationId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "친구에게 편지요청하는 API"
            , notes = "relationId를 받아 relation에 해당하는 인원에게 PostRequest를 생성. 반환값으로 Http status가 반환된다.")
    @PostMapping(value = "/post-requests")
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
            , notes = "memberId를 받고 반환값으로 sendDateTime과 friendId friendName list가 반환된다")
    @GetMapping(value = "/post-requests/{memberId}/receive")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getReceivedPostRequest(@PathVariable("memberId") String memberId) {
        logger.info("request is -> {}", memberId);
        try {
            List<GetReceivedPostRequestResponseDto> getReceivedPostRequestResponseDtos = friendRelationService.getReceivedPostRequest(memberId);
            return new ResponseEntity<>(getReceivedPostRequestResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "친구 검색결과를 가져오는 API"
            , notes = "String값을 받아 반환값으로 이와 Name또는 Id가 일치하는 user를 반환한다.")
    @GetMapping(value = "/search/{searchStr}")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getSearchFriendRequest(@PathVariable("searchStr") String searchStr) {
        logger.info("request is -> {}", searchStr);
        try {
            List<GetSearchedFriendResponseDto> GetSearchedFriendResponseDtos = friendRelationService.getSearchedFriendRequest(searchStr);
            return new ResponseEntity<>(GetSearchedFriendResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 편지를 요청하는 api
}
