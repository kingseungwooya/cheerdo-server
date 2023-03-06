package com.example.cheerdo.friends.service;

import com.example.cheerdo.friends.dto.request.RemoveOrAcceptRequestDto;
import com.example.cheerdo.friends.dto.request.SendPostRequestDto;
import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.GetFriendRequestResponseDto;
import com.example.cheerdo.friends.dto.response.GetReceivedPostRequestResponseDto;
import com.example.cheerdo.friends.dto.response.GetFriendResponseDto;
import com.example.cheerdo.friends.dto.response.GetSearchedFriendResponseDto;

import java.util.List;

public interface FriendRelationService {

    List<GetFriendResponseDto> getMyFriendList(String memberId) throws Exception;

    void sendRequest(SendRequestDto sendRequestDto) throws Exception;

    List<GetFriendRequestResponseDto> getMyRequest(String memberId) throws Exception;

    List<GetFriendResponseDto> getReceivedRequest(String memberId) throws Exception;

    void removeOrAcceptRequest(RemoveOrAcceptRequestDto removeOrAcceptRequestDto) throws Exception;

    void sendPostRequest(SendPostRequestDto sendPostRequestDto) throws Exception;

    List<GetReceivedPostRequestResponseDto> getReceivedPostRequest(String memberId) throws Exception;

    List<GetSearchedFriendResponseDto> getSearchedFriendRequest(String searchStr) throws Exception;

    void deleteRelation(Long relationId) throws Exception;

}
