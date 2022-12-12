package com.passionPay.passionPayBackEnd.group.dto;

import com.passionPay.passionPayBackEnd.group.domain.Group;
import com.passionPay.passionPayBackEnd.group.domain.GroupMission;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtherGroupDto {
    private Long groupId;
    private String groupName;
    private String groupDescription;
    private boolean groupPrivacy;
    private List<GroupMission> groupMissions;
    private int groupAvgStudyTime;
    private int maxMember;
    private String groupPassword;
    private ProgressDto groupAvgGoalProgresses;

    public static OtherGroupDto from(Group group, ProgressDto avg, int groupAvgStudyTime) {
        return OtherGroupDto.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .groupDescription(group.getGroupDescription())
                .groupPrivacy(!group.getGroupPassword().isBlank())
                .maxMember(group.getMaxMember())
                .groupPassword(group.getGroupPassword())
                .groupAvgGoalProgresses(avg)
                .groupAvgStudyTime(groupAvgStudyTime)
                .build();
    }
}
