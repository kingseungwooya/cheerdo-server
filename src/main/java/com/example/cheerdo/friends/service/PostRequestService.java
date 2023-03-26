package com.example.cheerdo.friends.service;

import com.example.cheerdo.friends.dto.response.PostRequestResponseDto;
import java.util.List;

public interface PostRequestService {

    void sendPostRequest(Long relationId);

    List<PostRequestResponseDto> getReceivedPostRequest(String memberId);

}
