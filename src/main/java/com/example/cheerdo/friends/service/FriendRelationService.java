package com.example.cheerdo.friends.service;

import com.example.cheerdo.friends.dto.response.FollowerResponseDto;

import java.util.List;

public interface FriendRelationService {

    List<FollowerResponseDto> getFriends(String memberId);

    void deleteFriend(Long relationId);

}
