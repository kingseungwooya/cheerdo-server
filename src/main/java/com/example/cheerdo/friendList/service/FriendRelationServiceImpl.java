package com.example.cheerdo.friendList.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friendList.dto.request.RemoveOrAcceptRequestDto;
import com.example.cheerdo.friendList.dto.request.SendRequestDto;
import com.example.cheerdo.friendList.dto.response.GetFriendRequestResponseDto;
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

    public void sendRequest(SendRequestDto sendRequestDto) throws Exception {
        Optional<Member> member = memberRepository.findById(sendRequestDto.getUserId());
        Optional<Member> friend = memberRepository.findById(sendRequestDto.getFriendId());
        if (member.isEmpty() | friend.isEmpty()) {
            throw new Exception("there is no such member");
        } else if (member.get().equals(friend.get())) {
            throw new Exception("friend and member are the same");
        }
        logger.info("member is -> {} friend is -> {}", member.get().getName(), friend.get().getName());

        Optional<FriendRelation> friendRelation = friendRelationRepository.findFriendRelationByMemberAndFriendId(member, sendRequestDto.getFriendId());
        if (!friendRelation.isEmpty()) {
            throw new Exception("friendRelation already exist");
        }
        friendRelationRepository.save(sendRequestDto.dtoToFriendRelationEntity(memberRepository.findById(sendRequestDto.getUserId())));
    }

    @Override
    public List<GetFriendRequestResponseDto> getMyRequest(String userId) throws Exception {
        Optional<Member> member = memberRepository.findById(userId);
        if (member.isEmpty()) {
            throw new Exception("there is no such member");
        }
        logger.info("member is -> {}", member.get().getName());

        Optional<List<FriendRelation>> friendRelations = friendRelationRepository.findAllByMemberAndIsFriend(member, false);
        logger.info("unacceptedFriendRelations size is -> {}", friendRelations.get().size());
        List<GetFriendRequestResponseDto> list = new ArrayList<>();

        if (friendRelations.get().isEmpty()) {
            throw new Exception("there are no unaccepted request");
        } else {
            for (FriendRelation relation : friendRelations.get()) {
                Optional<Member> friendMember = memberRepository.findById(relation.getFriendId());
                list.add(GetFriendRequestResponseDto.builder()
                        .name(friendMember.get().getName())
                        .id(relation.getId())
                        .build());
                logger.info("build complete");
            }
            logger.info("List complete");
            return list;
        }
    }

    @Override
    public List<LoadFriendResponseDto> getReceivedRequest(String userId) throws Exception {
        logger.info("member is -> {}", userId);

        Optional<List<FriendRelation>> friendRelations = friendRelationRepository.findAllByFriendIdAndIsFriend(userId, false);
        logger.info("receivedFriendRelations size is -> {}", friendRelations.get().size());
        List<LoadFriendResponseDto> list = new ArrayList<>();

        if (friendRelations.get().isEmpty()) {
            throw new Exception("there are no received request");
        } else {
            for (FriendRelation relation : friendRelations.get()) {
                list.add(LoadFriendResponseDto.builder()
                        .name(relation.getMember().getName())
                        .memberId(relation.getMember().getId())
                        .relationId(relation.getId())
                        .build());
                logger.info("build complete");
            }
            logger.info("List complete");
            return list;
        }
    }

    @Override
    public void removeOrAcceptRequest(RemoveOrAcceptRequestDto removeOrAcceptRequestDto) throws Exception {
        Optional<FriendRelation> optionalFriendRelation = friendRelationRepository.findById(removeOrAcceptRequestDto.getRelationId());
        if (optionalFriendRelation.isEmpty()) {
            throw new Exception("there is no such relation");
        }
        FriendRelation friendRelation = optionalFriendRelation.get();

        if (!removeOrAcceptRequestDto.isAccept()){ // 수락하지 않으면 = 삭제 요청
            logger.info("remove request");
            if (friendRelation.isFriend()) {
                String friendId = friendRelation.getMember().getId();
                Optional<Member> member = memberRepository.findById(friendRelation.getFriendId());
                FriendRelation reversedFriendRelation = friendRelationRepository.findFriendRelationByMemberAndFriendId(member, friendId).get();
                friendRelationRepository.delete(reversedFriendRelation);
                logger.info("the relation id {}, member {}, friendId {} is removed", reversedFriendRelation.getId(), reversedFriendRelation.getMember(), reversedFriendRelation.getFriendId());
            }
            friendRelationRepository.delete(friendRelation);
            logger.info("the relation id {}, member {}, friendId {} is removed", friendRelation.getId(), friendRelation.getMember(), friendRelation.getFriendId());
        } else { // 수락 요청
            logger.info("accept request");
            friendRelation.setFriend(true);
            String friendId = friendRelation.getMember().getId();
            Optional<Member> member = memberRepository.findById(friendRelation.getFriendId());
            if (member.isEmpty()) {
                throw new Exception("there is no such member");
            }

            Optional<FriendRelation> reversedFriendRelation = friendRelationRepository.findFriendRelationByMemberAndFriendId(member, friendId);
            if (reversedFriendRelation.isEmpty()) {
                friendRelationRepository.save(removeOrAcceptRequestDto.dtoToFriendRelationEntity(member, friendId));
                logger.info("the reversed Relation doesn't exist. save new relation");
            } else {
                reversedFriendRelation.get().setFriend(true);
                logger.info("the reversed Relation exists. set id {} isFrined false -> true", reversedFriendRelation.get().getId());
            }
        }
}
}
