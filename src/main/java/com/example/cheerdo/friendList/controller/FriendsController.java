package com.example.cheerdo.friendList.controller;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friendList.dto.request.AcceptRequestDto;
import com.example.cheerdo.friendList.dto.request.PutRequestDto;
import com.example.cheerdo.friendList.dto.request.removeRequestOrFriendDto;
import com.example.cheerdo.friendList.dto.response.GetFriendResponseDto;
import com.example.cheerdo.friendList.dto.response.LoadFriendResponseDto;
import com.example.cheerdo.friendList.service.FriendRelationService;
import com.example.cheerdo.todo.controller.TodoController;
import com.github.javafaker.Faker;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/friend")
@RequiredArgsConstructor
public class FriendsController {
    private final Logger logger = LoggerFactory.getLogger(FriendsController.class);
    private final FriendRelationService friendRelationService;

    @GetMapping(value = "/List{userId}")
    @ApiOperation(value = "userId의 초기 Friend 화면에 필요한 data를 가져오는 api"
            , notes = "반환값으로 relationId memberId name list가 반환된다")
    @ApiResponse(code = 200, message = "status ok")
    public ResponseEntity<?> getMyFriendList(@PathVariable("userId") String userId) {
        logger.info("request is -> {}", userId);
        try {
            System.out.println(userId);
            List<LoadFriendResponseDto> LoadFriendResponseDtos = (List<LoadFriendResponseDto>) friendRelationService.getFriendList(userId);
            return new ResponseEntity<>(LoadFriendResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

