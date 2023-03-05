package com.example.cheerdo.entity;

import com.example.cheerdo.post.dto.response.LetterResponseDto;
import com.example.cheerdo.post.dto.response.PostResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class PostRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "friendRelation")
    private FriendRelation friendRelation;

    // @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "send_date")
    private LocalDateTime sendDateTime;

    @Builder
    public PostRequest(Long id, FriendRelation friendRelation, LocalDateTime sendDateTime) {
        this.id = id;
        this.friendRelation = friendRelation;
        this.sendDateTime = sendDateTime;
    }
}
