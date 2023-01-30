package com.example.cheerdo.friendList.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friendList.controller.FriendsController;
import com.example.cheerdo.friendList.dto.request.PutRequestDto;
import com.example.cheerdo.friendList.dto.response.LoadFriendResponseDto;
import com.example.cheerdo.friendList.repository.FriendRelationRepository;
import com.example.cheerdo.friendList.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class FriendRelationServiceImpl implements FriendRelationService {
    private final MemberRepository memberRepository;
    private final FriendRelationRepository friendRelationRepository;
    @Override
    public List<?> getMyFriendList(String userId) throws Exception {
        // 반환값으로 relation id member id name을 가지는 LoadFriendRelationDto의 List가 반환된다.

        Optional<Member> member = memberRepository.findById(userId);
        Optional<List<FriendRelation>> friendRelations = friendRelationRepository.findAllByMember(member);
        List<LoadFriendResponseDto> list = new ArrayList<>();

        if (friendRelations.isEmpty()) {
            throw new Exception("친구가 없습니다");
        }

        for (FriendRelation relation : friendRelations.get()) {
            Optional<Member> frined_member = memberRepository.findById(relation.getFriendId());
            list.add(LoadFriendResponseDto.builder()
                    .name(frined_member.get().getName())
                    .relationId(relation.getId())
                    .memberId(relation.getFriendId())
                    .build());
        }
        return list;
    }

    public void putRequest(PutRequestDto putRequestDto) throws Exception {
        Long id = 5L;
        friendRelationRepository.save(putRequestDto.dtoToFriendRelationEntity(id, memberRepository.findById(putRequestDto.getUserId())));
    }

}
