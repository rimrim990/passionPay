package com.passionPay.passionPayBackEnd.group.repository;

import com.passionPay.passionPayBackEnd.group.domain.Group;
import com.passionPay.passionPayBackEnd.group.domain.GroupMember;
import com.passionPay.passionPayBackEnd.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    // save
    GroupMember save(GroupMember groupMember);

    // find
    Optional<GroupMember> findById(Long groupMemberId);
    Optional<GroupMember> findByMemberIdAndGroupId(Long memberId, Long groupId);
    List<GroupMember> findByMember(Member member);
    List<GroupMember> findByGroup(Group group);

    // delete
    void delete(GroupMember groupMember);
}
