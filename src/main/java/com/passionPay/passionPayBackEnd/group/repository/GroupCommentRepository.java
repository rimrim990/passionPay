package com.passionPay.passionPayBackEnd.group.repository;

import com.passionPay.passionPayBackEnd.group.domain.GroupPostComment;
import com.passionPay.passionPayBackEnd.group.domain.GroupMember;
import com.passionPay.passionPayBackEnd.group.domain.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupCommentRepository extends JpaRepository<GroupPostComment, Long> {
    // save
    GroupPostComment save(GroupPostComment groupPostComment);

    // find
    Optional<GroupPostComment> findById(Long groupCommentId);
    List<GroupPostComment> findByGroupPost(GroupPost groupPost);

    // delete
    void deleteById(Long groupCommentId);
    void delete(GroupPostComment groupPostComment);
    void deleteByGroupMember(GroupMember groupMember);
    void deleteByGroupPost(GroupPost groupPost);
}
