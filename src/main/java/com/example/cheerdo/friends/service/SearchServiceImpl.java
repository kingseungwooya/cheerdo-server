package com.example.cheerdo.friends.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friends.dto.response.SearchedFriendResponseDto;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.RelationRepository;
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
    private final RelationRepository relationRepository;

    @Override
    public List<SearchedFriendResponseDto> searchFriend(String keyword, String memberId) {
        Member member = memberRepository.findById(memberId).get();
        List<Member> searchedMembers = memberRepository.findAllByNameContainingIgnoreCaseAndIdContainingIgnoreCase(
                keyword, keyword);

        // 이미 친구이거나, 본인의 경우 제외, 친구 요청 보낸 상대도 제외
        return searchedMembers.stream()
                .filter(m -> !relationRepository.existsByMemberAndFriendId(member, m.getId())
                        || m.getId() == memberId
                ).map(
                        e -> e.to()
                ).collect(Collectors.toList());
    }
}
