package com.example.cheerdo.friendList.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friendList.dto.request.SendRequestDto;
import com.example.cheerdo.friendList.dto.response.LoadFriendResponseDto;
import com.example.cheerdo.friendList.repository.FriendRelationRepository;
import com.example.cheerdo.friendList.repository.MemberRepository;
import com.github.javafaker.Faker;
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
    private final Logger logger = LoggerFactory.getLogger(FriendRelationServiceImpl.class);
    private final MemberRepository memberRepository;
    private final FriendRelationRepository friendRelationRepository;
    private final Faker faker = new Faker();
    @Override
    public List<LoadFriendResponseDto> getMyFriendList(String userId) throws Exception {
        // 반환값으로 relation id member id name을 가지는 LoadFriendRelationDto의 List가 반환된다.

        Optional<Member> member = memberRepository.findById(userId);
        if (member.isEmpty()) {
            throw new Exception("there is no such member");
        }
        logger.info("member is -> {}", member.get().getName());

        Optional<List<FriendRelation>> friendRelations = friendRelationRepository.findAllByMemberAndIsFriend(member, true);
        logger.info("friendRelations size is -> {}", friendRelations.get().size());
        List<LoadFriendResponseDto> list = new ArrayList<>();

        if (friendRelations.get().isEmpty()) {
            throw new Exception("member has no friends");
        } else {
            for (FriendRelation relation : friendRelations.get()) {
                Optional<Member> friendMember = memberRepository.findById(relation.getFriendId());
                list.add(LoadFriendResponseDto.builder()
                        .name(friendMember.get().getName())
                        .relationId(relation.getId())
                        .memberId(relation.getFriendId())
                        .build());
                logger.info("build complete");
            }
            logger.info("List complete");
            return list;
        }
    }

    public void putRequest(PutRequestDto putRequestDto) throws Exception {
        Long id = faker.number().randomNumber();
        Optional<Member> member = memberRepository.findById(putRequestDto.getUserId());
        Optional<Member> friend = memberRepository.findById(putRequestDto.getFriendId());
        if (member.isEmpty() | friend.isEmpty()) {
            throw new Exception("there is no such member");
        }
        logger.info("member is -> {} friend is -> {}", member.get().getName(), friend.get().getName());

        Optional<FriendRelation> friendRelation = friendRelationRepository.findFriendRelationByMemberAndFriendId(member, putRequestDto.getFriendId());
        if (!friendRelation.isEmpty()) {
            throw new Exception("friendRelation already exist");
        }
        friendRelationRepository.save(putRequestDto.dtoToFriendRelationEntity(id, memberRepository.findById(putRequestDto.getUserId())));
    }

}
