package com.passionPay.passionPayBackEnd.group.payload;

import com.passionPay.passionPayBackEnd.group.domain.Group;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGroupPayload {
    private String groupName;
    private String groupDescription;
    private int groupTimeGoal;
    private int maxMember;
    private List<String> groupMissions;
    private boolean groupPrivacy;
    private String groupPassword;

    public static Group from(CreateGroupPayload groupRequestDto) {
        return Group.builder()
                .groupName(groupRequestDto.getGroupName())
                .groupTimeGoal(groupRequestDto.getGroupTimeGoal())
                .groupPassword(groupRequestDto.getGroupPassword())
                .groupDescription(groupRequestDto.getGroupDescription())
                .maxMember(groupRequestDto.getMaxMember())
                .build();
    }
}
