package com.example.cheerdo.friends.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friends.dto.response.FollowerResponseDto;
import com.example.cheerdo.repository.PostRepository;
import com.example.cheerdo.repository.PostRequestRepository;
import com.example.cheerdo.repository.RelationRepository;
import com.example.cheerdo.repository.MemberRepository;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class FriendRelationServiceImpl implements FriendRelationService {
    private final MemberRepository memberRepository;
    private final RelationRepository friendRelationRepository;
    private final PostRepository postRepository;
    private final PostRequestRepository postRequestRepository;

    @Override
    public List<FollowerResponseDto> getFriends(String memberId) {
        Member member = memberRepository.findById(memberId).get();
        List<FriendRelation> relations = friendRelationRepository.findAllByMemberAndIsFriend(member, true).get();

        return relations.stream().map(
                r -> r.to(memberRepository.findById(r.getFriendId())
                        .get()
                        .getId())
        ).collect(Collectors.toList());

    }

    @Override
    public void deleteFriend(Long relationId) {
        FriendRelation friendRelation = friendRelationRepository.findById(relationId).get();
        Member friend = memberRepository.findById(friendRelation.getFriendId()).get();
        FriendRelation reversedfriendRelation = friendRelationRepository.findFriendRelationByMemberAndFriendId(friend,
                friendRelation.getMember().getId()).get();
        postRequestRepository.deleteAllByFriendRelation(friendRelation);
        postRequestRepository.deleteAllByFriendRelation(reversedfriendRelation);
        postRepository.deleteAllByRelation(friendRelation);
        postRepository.deleteAllByRelation(reversedfriendRelation);
        friendRelationRepository.delete(friendRelation);
        friendRelationRepository.delete(reversedfriendRelation);
    }
}
