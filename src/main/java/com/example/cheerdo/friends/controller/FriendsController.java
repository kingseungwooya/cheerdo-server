package com.example.cheerdo.friends.controller;

import com.example.cheerdo.friends.dto.response.FollowerResponseDto;
import com.example.cheerdo.friends.dto.response.SearchedFriendResponseDto;
import com.example.cheerdo.friends.service.FriendRelationService;
import com.example.cheerdo.friends.service.SearchService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member/friend")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendRelationService friendRelationService;
    private final SearchService searchService;

    @GetMapping(value = "/")
    @ApiOperation(value = "memberId의 초기 Friend 화면에 필요한 data를 가져오는 api")
    public ResponseEntity<List<FollowerResponseDto>> getMyFriends(@RequestParam String memberId) {

        List<FollowerResponseDto> followerResponseDtos = friendRelationService.getFriends(memberId);
        return new ResponseEntity<>(followerResponseDtos, HttpStatus.OK);

    }

    @DeleteMapping(value = "/")
    @ApiOperation(value = "친구를 삭제하는 api")
    public ResponseEntity<?> removeFriend(@RequestParam Long relationId) {

        friendRelationService.deleteFriend(relationId);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @ApiOperation(value = "친구 검색결과를 가져오는 API"
            , notes = "String값을 받아 반환값으로 이와 Name또는 Id가 일치하는 user를 반환한다.")
    @GetMapping(value = "/search")
    public ResponseEntity<List<SearchedFriendResponseDto>> searchMember(@RequestParam String keyword) {
        return new ResponseEntity<>(searchService.searchFriend(keyword), HttpStatus.OK);
    }

}
