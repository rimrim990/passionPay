package com.passionPay.passionPayBackEnd.group.repository;

import com.passionPay.passionPayBackEnd.group.domain.GroupComment;
import com.passionPay.passionPayBackEnd.group.domain.GroupMember;
import com.passionPay.passionPayBackEnd.group.domain.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupCommentRepository extends JpaRepository<GroupComment, Long> {
    // save
    GroupComment save(GroupComment groupComment);

    // find
    Optional<GroupComment> findById(Long groupCommentId);
    List<GroupComment> findByGroupPost(GroupPost groupPost);

    // delete
    void deleteById(Long groupCommentId);
    void delete(GroupComment groupComment);
    void deleteByGroupMember(GroupMember groupMember);
    void deleteByGroupPost(GroupPost groupPost);
}
