package com.example.cheerdo.friends.service;

import com.example.cheerdo.friends.dto.response.SearchedFriendResponseDto;
import java.util.List;

public interface SearchService {

    List<SearchedFriendResponseDto> searchFriend(String keyword);
}
