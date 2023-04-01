package com.example.cheerdo.friends.service;

import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.SentFollowResponseDto;
import com.example.cheerdo.friends.dto.response.FollowerResponseDto;
import java.util.List;

public interface FollowService {

    void sendFollow(SendRequestDto sendRequestDto);

    List<SentFollowResponseDto> getSentFollow(String memberId);

    List<FollowerResponseDto> getFollowRequest(String memberId);

    void accept(Long relationId);

    void refuse(Long relationId);

}
