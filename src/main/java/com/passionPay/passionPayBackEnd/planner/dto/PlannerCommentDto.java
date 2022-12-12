package com.passionPay.passionPayBackEnd.planner.dto;

import com.passionPay.passionPayBackEnd.member.dto.MemberInfoDto;
import com.passionPay.passionPayBackEnd.planner.domain.PlannerComment;
import com.passionPay.passionPayBackEnd.util.DateUtil;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannerCommentDto {
    private Long plannerCommentId;
    private String content;
    private MemberInfoDto memberInfo;
    private String createdAt;

    public static PlannerCommentDto from(PlannerComment plannerComment) {
        MemberInfoDto memberInfo = MemberInfoDto.of(plannerComment.getMember());
        return PlannerCommentDto.builder()
                .plannerCommentId(plannerComment.getId())
                .content(plannerComment.getContent())
                .createdAt(DateUtil.formatDateTimeToString(plannerComment.getCreatedAt()))
                .memberInfo(memberInfo)
                .build();
    }
}
