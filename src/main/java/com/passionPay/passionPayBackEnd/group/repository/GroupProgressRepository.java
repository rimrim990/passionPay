package com.passionPay.passionPayBackEnd.group.repository;

import com.passionPay.passionPayBackEnd.group.domain.Group;
import com.passionPay.passionPayBackEnd.group.domain.GroupMissionProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupProgressRepository extends JpaRepository<GroupMissionProgress, Long> {
    // save
    GroupMissionProgress save(GroupMissionProgress groupMissionProgress);

    // find
    // 내 미션 달성률
    @Query("SELECT COUNT(gp.id) " +
            "FROM GroupMissionProgress gp INNER JOIN gp.groupMission gm " +
            "WHERE gp.groupMemberId = :groupMemberId AND " +
            "gp.complete = true AND gm.group = :group")
    int findCountByGroupMemberAndGroupMission(@Param("groupMemberId") Long groupMemberId, @Param("group") Group group);

    // 그룹 평균 미션 달성률
    @Query("SELECT COUNT(gp.id) " +
            "FROM GroupMissionProgress gp INNER JOIN gp.groupMission gm " +
            "WHERE gp.complete = true AND gm.group = :group")
    int findCountByGroupMission(@Param("group") Group group);

    // update

    // delete
    void delete(GroupMissionProgress groupMissionProgress);
    void deleteById(Long groupProgressId);
}
