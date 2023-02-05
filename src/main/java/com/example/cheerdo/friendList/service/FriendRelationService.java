package com.example.cheerdo.friendList.service;

import com.example.cheerdo.friendList.dto.request.SendRequestDto;
import com.example.cheerdo.friendList.dto.response.LoadFriendResponseDto;

import java.util.List;

public interface FriendRelationService {

    List<LoadFriendResponseDto> getMyFriendList(String userId) throws Exception;

    void sendRequest(SendRequestDto sendRequestDto) throws Exception;
}
