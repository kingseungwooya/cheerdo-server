package com.example.cheerdo.friends.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friends.dto.response.SearchedFriendResponseDto;
import com.example.cheerdo.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class SearchServiceImpl implements SearchService {
    private final MemberRepository memberRepository;

    @Override
    public List<SearchedFriendResponseDto> searchFriend(String keyword) {
        List<Member> searchedMembers = memberRepository.findAllByNameContainingIgnoreCaseAndIdContainingIgnoreCase(
                keyword, keyword);

        return searchedMembers.stream().map(
                    m -> m.to()
                 ).collect(Collectors.toList());
    }
}
