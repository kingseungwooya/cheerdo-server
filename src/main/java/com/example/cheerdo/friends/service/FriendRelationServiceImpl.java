package com.example.cheerdo.friends.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.PostRequest;
import com.example.cheerdo.friends.dto.request.RemoveOrAcceptRequestDto;
import com.example.cheerdo.friends.dto.request.SendPostRequestDto;
import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.GetFriendRequestResponseDto;
import com.example.cheerdo.friends.dto.response.GetReceivedPostRequestResponseDto;
import com.example.cheerdo.friends.dto.response.GetFriendResponseDto;
import com.example.cheerdo.friends.dto.response.GetSearchedFriendResponseDto;
import com.example.cheerdo.repository.PostRequestRepository;
import com.example.cheerdo.repository.RelationRepository;
import com.example.cheerdo.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class FriendRelationServiceImpl implements FriendRelationService {
    private final Logger logger = LoggerFactory.getLogger(FriendRelationServiceImpl.class);
    private final MemberRepository memberRepository;
    private final RelationRepository friendRelationRepository;
    private final PostRequestRepository postRequestRepository;
    @Override
    public List<GetFriendResponseDto> getMyFriendList(String memberId) throws Exception {
        // 반환값으로 relation id member id name을 가지는 LoadFriendRelationDto의 List가 반환된다.

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new Exception("there is no such member"));
        logger.info("member is -> {}", member.getName());

        List<FriendRelation> friendRelations = friendRelationRepository.findAllByMemberAndIsFriend(member, true).orElseThrow(
                () -> new Exception("member has no friends")
        );
        logger.info("friendRelations size is -> {}", friendRelations.size());
        List<GetFriendResponseDto> list = new ArrayList<>();

        for (FriendRelation relation : friendRelations) {
            Optional<Member> friendMember = memberRepository.findById(relation.getFriendId());
            list.add(GetFriendResponseDto.builder()
                    .name(friendMember.get().getName())
                    .relationId(relation.getId())
                    .memberId(relation.getFriendId())
                    .build());
            logger.info("build complete");
        }
        logger.info("List complete");
        return list;

    }

    public void sendRequest(SendRequestDto sendRequestDto) throws Exception {
        Member member = memberRepository.findById(sendRequestDto.getMemberId()).orElseThrow(
                () -> new Exception("there is no such member")
        );
        Member friend = memberRepository.findById(sendRequestDto.getFriendId()).orElseThrow(
                () -> new Exception("there is no such member")
        );

        logger.info("member is -> {} friend is -> {}", member.getName(), sendRequestDto.getFriendId());

        Optional<FriendRelation> friendRelation = friendRelationRepository.findFriendRelationByMemberAndFriendId(member, sendRequestDto.getFriendId());
        if (!friendRelation.isEmpty()) {
            throw new Exception("friendRelation already exist");
        }
        friendRelationRepository.save(sendRequestDto.dtoToFriendRelationEntity(memberRepository.findById(sendRequestDto.getMemberId()).get()));
    }

    @Override
    public List<GetFriendRequestResponseDto> getMyRequest(String memberId) throws Exception {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new Exception("there is no such member")
        );
        logger.info("member is -> {}", member.getName());

        List<FriendRelation> friendRelations = friendRelationRepository.findAllByMemberAndIsFriend(member, false).orElseThrow(
                () -> new Exception("there are no unaccepted request")
        );
        logger.info("unacceptedFriendRelations size is -> {}", friendRelations.size());
        List<GetFriendRequestResponseDto> list = new ArrayList<>();

        for (FriendRelation relation : friendRelations) {
            Optional<Member> friendMember = memberRepository.findById(relation.getFriendId());
            list.add(GetFriendRequestResponseDto.builder()
                    .name(friendMember.get().getName())
                    .relationId(relation.getId())
                    .build());
            logger.info("build complete");
        }
        logger.info("List complete");
        return list;

    }

    @Override
    public List<GetFriendResponseDto> getReceivedRequest(String memberId) throws Exception {
        logger.info("member is -> {}", memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new Exception("there is no such member")
        );
        List<FriendRelation> friendRelations = friendRelationRepository.findAllByFriendIdAndIsFriend(memberId, false).orElseThrow(
                () -> new Exception("there are no received request")
        );
        logger.info("receivedFriendRelations size is -> {}", friendRelations.size());
        List<GetFriendResponseDto> list = new ArrayList<>();

        for (FriendRelation relation : friendRelations) {
            list.add(GetFriendResponseDto.builder()
                    .name(relation.getMember().getName())
                    .memberId(relation.getMember().getId())
                    .relationId(relation.getId())
                    .build());
            logger.info("build complete");
        }
        logger.info("List complete");
        return list;

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
                FriendRelation reversedFriendRelation = friendRelationRepository.findFriendRelationByMemberAndFriendId(member.get(), friendId).get();
                friendRelationRepository.delete(reversedFriendRelation);
                logger.info("the relation id {}, member {}, friendId {} is removed", reversedFriendRelation.getId(), reversedFriendRelation.getMember(), reversedFriendRelation.getFriendId());
            }
            friendRelationRepository.delete(friendRelation);
            logger.info("the relation id {}, member {}, friendId {} is removed", friendRelation.getId(), friendRelation.getMember(), friendRelation.getFriendId());
        } else { // 수락 요청
            logger.info("accept request");
            friendRelation.setFriend(true);
            String friendId = friendRelation.getMember().getId();
            Member member = memberRepository.findById(friendRelation.getFriendId()).orElseThrow(
                    () -> new Exception("there is no such member")
            );

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

    @Override
    public void sendPostRequest(SendPostRequestDto sendPostRequestDto) throws Exception {
        FriendRelation friendRelation = friendRelationRepository.findById(sendPostRequestDto.getRelationId()).orElseThrow(
                () -> new Exception("there is no such relation")
        );
        Member member = memberRepository.findById(friendRelation.getFriendId()).orElseThrow(
                () -> new Exception("there is no such member")
        );
        postRequestRepository.save(sendPostRequestDto.dtoToPostRequestEntity(friendRelation));
    }

    @Override
    public List<GetReceivedPostRequestResponseDto> getReceivedPostRequest(String memberId) throws Exception {
        List<GetReceivedPostRequestResponseDto> list = new ArrayList<>();
        List<PostRequest> postRequests = postRequestRepository.findAllByFriendRelation_Member_Id(memberId).orElseThrow(
                () -> new Exception("there is no such request")
        );
        for ( int i = 0; i < postRequests.size(); i++ ) {
            Optional<Member> friend = memberRepository.findById(postRequests.get(i).getFriendRelation().getFriendId());

            list.add(GetReceivedPostRequestResponseDto.builder()
                    .sendDateTime(postRequests.get(i).getSendDateTime())
                    .friendName(friend.get().getName())
                    .friendId(friend.get().getId())
                    .build()
            );
        }
        return list;
    }

    @Override
    public List<GetSearchedFriendResponseDto> getSearchedFriendRequest(String searchStr) throws Exception {
        List<GetSearchedFriendResponseDto> list = new ArrayList<>();
        Optional<List<Member>> searchedMembers1 = memberRepository.findAllByIdContainingIgnoreCase(searchStr);
        Optional<List<Member>> searchedMembers2 = memberRepository.findAllByNameContainingIgnoreCase(searchStr);
        logger.info("searched memberId length -> {}, searched memberName length -> {}", searchedMembers1.get().size(), searchedMembers2.get().size());

        List<Member> searchedMembers = searchedMembers2.get();
        searchedMembers.addAll(searchedMembers1.get());
        if (searchedMembers.size() == 0) {
            throw new Exception("no search data");
        } else {
            HashSet<Member> memberHashSet = new HashSet<Member>();
            memberHashSet.addAll(searchedMembers);
            Iterator iterator = memberHashSet.iterator();

            while(iterator.hasNext()) {
                Member tempMember = (Member) iterator.next();
                list.add(GetSearchedFriendResponseDto.builder()
                                .memberId(tempMember.getId())
                                .name(tempMember.getName())
                                .image(tempMember.getMemberImage())
                                .build()
                );
            }
        }

        return list;
    }

    @Override
    public void deleteRelation(Long relationId) throws Exception {
        FriendRelation friendRelation = friendRelationRepository.findById(relationId).orElseThrow(
                () -> new Exception("there is no such relation")
        );
        Member friend = memberRepository.findById(friendRelation.getFriendId()).get();
        FriendRelation reversedfriendRelation = friendRelationRepository.findFriendRelationByMemberAndFriendId(friend, friendRelation.getMember().getId()).orElseThrow(
                () -> new Exception("there is no reversed relation. please use request control")
        );
        friendRelationRepository.delete(friendRelation);
        friendRelationRepository.delete(reversedfriendRelation);
    }
}
