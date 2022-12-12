package com.passionPay.passionPayBackEnd.group.dto;

import com.passionPay.passionPayBackEnd.member.dto.MemberInfoDto;
import com.passionPay.passionPayBackEnd.group.domain.GroupPostComment;
import com.passionPay.passionPayBackEnd.util.DateUtil;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupCommentDto {
    private Long groupCommentId;
    private String content;
    private MemberInfoDto memberInfo;
    private String createdAt;

    public static GroupCommentDto from(GroupPostComment groupPostComment) {
        MemberInfoDto memberInfo = MemberInfoDto.of(groupPostComment.getGroupMember().getMember());
        return GroupCommentDto.builder()
                .groupCommentId(groupPostComment.getId())
                .memberInfo(memberInfo)
                .content(groupPostComment.getContent())
                .createdAt(DateUtil.formatDateTimeToString(groupPostComment.getCreatedAt()))
                .build();
    }
}
