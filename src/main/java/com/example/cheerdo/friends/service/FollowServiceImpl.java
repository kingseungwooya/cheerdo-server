package com.example.cheerdo.friends.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.SentFollowResponseDto;
import com.example.cheerdo.friends.dto.response.FollowerResponseDto;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.RelationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {

    private final MemberRepository memberRepository;
    private final RelationRepository friendRelationRepository;

    @Override
    public void sendFollow(SendRequestDto sendRequestDto) throws IllegalArgumentException {
        Member member = memberRepository.findById(sendRequestDto.getMemberId()).get();

        friendRelationRepository.findFriendRelationByMemberAndFriendId(member, sendRequestDto.getFriendId())
                .orElseThrow(
                        () -> new IllegalArgumentException("friendRelation already exist")
                );

        friendRelationRepository.save(sendRequestDto.dtoToFriendRelationEntity(member));
    }

    @Override
    public List<SentFollowResponseDto> getSentFollow(String memberId) {
        Member member = memberRepository.findById(memberId).get();

        List<FriendRelation> friendRelations = friendRelationRepository.findAllByMemberAndIsFriend(member, false).get();

        return friendRelations.stream()
                .map(
                        r -> SentFollowResponseDto.builder()
                                .name(memberRepository.findById(r.getFriendId()).get().getName())
                                .relationId(r.getId())
                                .build()
                ).collect(Collectors.toList());
    }

    @Override
    public List<FollowerResponseDto> getFollowRequest(String memberId) {
        List<FriendRelation> friendRelations = friendRelationRepository.findAllByFriendIdAndIsFriend(memberId, false)
                .get();
        return friendRelations.stream()
                .map(
                        r -> FollowerResponseDto.builder()
                                .name(r.getMember().getName())
                                .relationId(r.getId())
                                .memberId(r.getMember().getId())
                                .build()
                ).collect(Collectors.toList());
    }

    @Override
    public void accept(Long relationId) {
        FriendRelation relation = friendRelationRepository.findById(relationId).get();
        relation.accept();
        Member member = memberRepository.findById(relation.getFriendId()).get();

        friendRelationRepository.save(
                FriendRelation.builder()
                        .member(member)
                        .friendId(relation.getMember().getId())
                        .isFriend(true)
                        .build()
        );
    }

    @Override
    public void refuse(Long relationId) {
        FriendRelation relation = friendRelationRepository.findById(relationId).get();
        friendRelationRepository.delete(relation);
    }

}
