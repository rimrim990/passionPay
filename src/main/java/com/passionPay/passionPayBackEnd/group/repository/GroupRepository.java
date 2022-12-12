package com.passionPay.passionPayBackEnd.group.repository;

import com.passionPay.passionPayBackEnd.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    // save
    Group save(Group group);

    // find
    Optional<Group> findById(Long groupId);
    List<Group> findAllByOrderByGroupIdDesc();

    // 내가 속한 그룹들 조회
    @Query("SELECT g " +
            "FROM Group g INNER JOIN g.groupMembers gm " +
            "WHERE gm.memberId = :memberId")
    List<Group> findMyGroupsByMemberId(@Param("memberId") Long memberId);

    // 그 외 그룹들 조회
    @Query("SELECT g " +
            "FROM Group g INNER JOIN g.groupMembers gm " +
            "WHERE not gm.memberId = :memberId")
    List<Group> findOtherGroupsByMemberId(@Param("memberId") Long memberId);

    // delete
    void delete(Group group);
    void deleteById(Long groupId);
}
