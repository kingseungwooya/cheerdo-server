package com.example.cheerdo.member.service;


import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Habit;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.member.dto.request.UpdateProfileRequestDto;
import com.example.cheerdo.member.dto.response.FriendInfoResponseDto;
import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.repository.HabitRepository;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.PostRepository;
import com.example.cheerdo.repository.RelationRepository;
import com.example.cheerdo.repository.TodoRepository;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;
    private final HabitRepository habitRepository;
    private final PostRepository postRepository;
    private final RelationRepository relationRepository;

    @Override
    public String updateMyInfo(UpdateProfileRequestDto updateProfileRequestDto) throws IOException {
        Member member = memberRepository.findById(updateProfileRequestDto.getMemberId()).get();
        member.updateMemberImage(updateProfileRequestDto.getUploadImage());
        member.updateBio(updateProfileRequestDto.getUpdateBio());
        member.updateName(updateProfileRequestDto.getUpdateName());
        memberRepository.save(member);
        final String successMessage = "profile is successly changed";
        return successMessage;
    }

    @Override
    public MemberInfoResponseDto getMyInfo(String memberId) {
        Member member = memberRepository.findById(memberId).get();

        Optional<Habit> habit = habitRepository.findTopByMemberOrderByStartDateDesc(member);

        // 내가 받은 편지 개수
        long receivePostCount = postRepository.countAllByReceiverId(memberId);
        // 내가 보낸 편지 개수
        Optional<List<FriendRelation>> relations = relationRepository.findAllByMemberAndIsFriend(member, true);
        long sendPostCount = 0;
        if(!relations.get().isEmpty()) {
            sendPostCount = relationRepository.findAllByMemberAndIsFriend(member, true).get()
                    .stream()
                    .map( r -> postRepository.countAllByRelation(r))
                    .reduce(Long::sum)
                    .get();
        }
        if (habit.isPresent()) {
            return member.to(habit.get().getDDay(), sendPostCount, receivePostCount);
        }
        return member.to(0, sendPostCount, receivePostCount);
    }

    @Override
    public FriendInfoResponseDto getFriendInfo(Long relationId) {
        FriendRelation meToFriendRelation = relationRepository.findById(relationId).get();
        // 내가 보낸 개수
        Long sendLetterCount =postRepository.countAllByRelation(meToFriendRelation);
        // 내가 이 친구한테 받은 개수
        String myId = meToFriendRelation.getMember().getId();

        String friendId = meToFriendRelation.getFriendId();
        Member friend = memberRepository.findById(friendId).get();
        FriendRelation friendToMeRelation = relationRepository.findFriendRelationByMemberAndFriendId(
                friend, myId
        ).get();

        Long getLetterCount = postRepository.countAllByRelation(friendToMeRelation);

        Optional<Habit> habit = habitRepository.findTopByMemberOrderByStartDateDesc(friend);
        if ( habit.isPresent() ) {
            return friend.to(sendLetterCount, getLetterCount, habit.get().getDDay());
        }
        return friend.to(sendLetterCount, getLetterCount, 0);

    }

}


