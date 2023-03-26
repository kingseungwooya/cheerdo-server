package com.example.cheerdo.entity;

import com.example.cheerdo.friends.dto.response.PostRequestResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class PostRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_request_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "relation_id")
    private FriendRelation friendRelation;

    @Column(name = "send_date_time")
    private LocalDateTime sendDateTime;

    @Builder
    public PostRequest(Long id, FriendRelation friendRelation, LocalDateTime sendDateTime) {
        this.id = id;
        this.friendRelation = friendRelation;
        this.sendDateTime = sendDateTime;
    }

    public PostRequestResponseDto to() {
        return PostRequestResponseDto.builder()
                .friendId(friendRelation.getMember().getId())
                .friendName(friendRelation.getMember().getName())
                .relationId(friendRelation.getId())
                .sendDateTime(sendDateTime)
                .memberImage(friendRelation.getMember().getMemberImage())
                .build();

    }
}
