package com.example.cheerdo.friends.repository;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
    Optional<List<FriendRelation>> findAllByMemberAndIsFriend(Optional<Member> member, boolean isFriend);
    Optional<FriendRelation> findFriendRelationByMemberAndFriendId(Optional<Member> member, String friendId);
    Optional<List<FriendRelation>> findAllByFriendIdAndIsFriend(String friendId, boolean isFriend);
}
