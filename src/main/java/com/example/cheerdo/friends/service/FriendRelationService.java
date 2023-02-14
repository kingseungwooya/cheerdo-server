package com.example.cheerdo.friends.service;

import com.example.cheerdo.friends.dto.request.RemoveOrAcceptRequestDto;
import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.GetFriendRequestResponseDto;
import com.example.cheerdo.friends.dto.response.LoadFriendResponseDto;

import java.util.List;

public interface FriendRelationService {

    List<LoadFriendResponseDto> getMyFriendList(String userId) throws Exception;

    void sendRequest(SendRequestDto sendRequestDto) throws Exception;

    List<GetFriendRequestResponseDto> getMyRequest(String userId) throws Exception;

    List<LoadFriendResponseDto> getReceivedRequest(String userId) throws Exception;

    void removeOrAcceptRequest(RemoveOrAcceptRequestDto removeOrAcceptRequestDto) throws Exception;

}
