package com.passionPay.passionPayBackEnd.group.dto;

import com.passionPay.passionPayBackEnd.member.dto.MemberInfoDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupProgressDto {
    private long groupProgressId;
    private String missionName;
    private MemberInfoDto memberInfo;
    private boolean completed;
}
