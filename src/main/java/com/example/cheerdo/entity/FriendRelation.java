package com.example.cheerdo.entity;

import lombok.Setter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "friend_relation")
@Getter
@Setter
@NoArgsConstructor
public class FriendRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @Column(name = "friend_id")
    private String friendId;

    @Column(name = "friend_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isFriend;


    @Builder
    public FriendRelation(Long id, Member member, String friendId, boolean isFriend) {
        this.id = id;
        this.member = member;
        this.friendId = friendId;
        this.isFriend = isFriend;
    }
    @Builder
    public FriendRelation(Long id, Member member, String friendId) {
        this.id = id;
        this.member = member;
        this.friendId = friendId;
        this.isFriend = false;
    }
}
