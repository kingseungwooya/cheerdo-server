package com.example.cheerdo.friends.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.PostRequest;
import com.example.cheerdo.friends.dto.response.PostRequestResponseDto;
import com.example.cheerdo.repository.PostRequestRepository;
import com.example.cheerdo.repository.RelationRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class PostRequestServiceImpl implements PostRequestService {
    private final RelationRepository friendRelationRepository;
    private final PostRequestRepository postRequestRepository;

    @Override
    public void sendPostRequest(Long relationId) {
        FriendRelation relation = friendRelationRepository.findById(relationId).get();
        postRequestRepository.save(
                PostRequest.builder()
                        .friendRelation(relation)
                        .sendDateTime(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public List<PostRequestResponseDto> getReceivedPostRequest(String memberId) {
        return postRequestRepository.findAllByFriendRelation_FriendId(memberId).get()
                .stream()
                .map( p -> p.to())
                .collect(Collectors.toList());
    }
}
