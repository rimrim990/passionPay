package com.passionPay.passionPayBackEnd.group.repository;

import com.passionPay.passionPayBackEnd.group.domain.GroupPostLike;
import com.passionPay.passionPayBackEnd.group.domain.GroupMember;
import com.passionPay.passionPayBackEnd.group.domain.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupLikeRepository extends JpaRepository<GroupPostLike, Long> {
    // save
    GroupPostLike save(GroupPostLike groupPostLike);

    // count by post
    @Query("SELECT COUNT(gl.id) " +
            "FROM GroupPostLike gl INNER JOIN gl.groupPost gp " +
            "WHERE gp = :groupPost")
    Long findCountByGroupPost(@Param("groupPost") GroupPost groupPost);

    // delete
    void delete(GroupPostLike groupPostLike);
    void deleteByGroupMember(GroupMember groupMember);
    void deleteByGroupPost(GroupPost groupPost);
}
