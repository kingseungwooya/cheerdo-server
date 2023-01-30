package com.example.cheerdo.friendList.service;

import com.example.cheerdo.friendList.dto.request.PutRequestDto;

import java.util.List;

public interface FriendRelationService {

    List<?> getMyFriendList(String userId) throws Exception;

    void putRequest(PutRequestDto putRequestDto) throws Exception;
}
