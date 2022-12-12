package com.passionPay.passionPayBackEnd.group.payload;

import com.passionPay.passionPayBackEnd.group.domain.GroupMember;
import com.passionPay.passionPayBackEnd.group.domain.GroupPost;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGroupPostPayload {
    private Long memberId;
    private String content;
    private String photoUrl;

    public static GroupPost from(CreateGroupPostPayload createGroupPostPayload, GroupMember groupMember) {
        return GroupPost.builder()
                .content(createGroupPostPayload.getContent())
                .groupMember(groupMember)
                .photoUrl(createGroupPostPayload.getPhotoUrl())
                .build();
    }
}
