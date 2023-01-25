package com.example.cheerdo.post.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Post;
import com.example.cheerdo.post.dto.request.LetterRequestDto;
import com.example.cheerdo.post.dto.request.PostRequestDto;
import com.example.cheerdo.post.dto.response.LetterResponseDto;
import com.example.cheerdo.post.dto.response.PostResponseDto;
import com.example.cheerdo.post.repository.PostRepository;
import com.example.cheerdo.post.repository.RelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.management.relation.Relation;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final RelationRepository relationRepository;

    @Override
    public List<?> getMyPosts(PostRequestDto postRequestDto) throws Exception {
        List<Post> posts = postRepository.findAllByReceiverIdAndIsOpenOrderBySendDateTime
                        (postRequestDto.getMemberId(), postRequestDto.isOpen())
                .orElseThrow(() -> new Exception("현재 등록되어 있는 친구가 없습니다."));
        if (postRequestDto.isOpen()) {
            return posts.stream()
                    .map(post -> post.entityToLetterResponseDto())
                    .collect(Collectors.toList());
        }
        return posts.stream()
                .map(post -> post.entityToPostResponseDto())
                .collect(Collectors.toList());
    }

    @Override
    public LetterResponseDto readPost(Long letterId) {
        // Coin에 대한 처리 해야함
        return postRepository.findById(letterId).get()
                .entityToLetterResponseDto();
    }

    @Override
    public void writeLetter(LetterRequestDto letterRequestDto) {
        FriendRelation relation = relationRepository.findById(letterRequestDto.getRelationId()).get();
        postRepository.save(letterRequestDto.dtoToPostEntity(relation));
    }

    private List<FriendRelation> getMyRelation(Long memberId) throws Exception {
        Optional<List<FriendRelation>> friendRelation = relationRepository.findAllByMember_Id(memberId);
        return friendRelation.orElseThrow(() -> new Exception("현재 등록되어 있는 친구가 없습니다."));
    }
}
