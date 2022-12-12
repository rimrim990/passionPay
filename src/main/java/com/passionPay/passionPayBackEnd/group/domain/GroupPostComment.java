package com.passionPay.passionPayBackEnd.group.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="GroupPostComment")
@Table(name="group_post_comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupPostComment {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    private GroupPost groupPost;

    // 댓글 작성자
    @ManyToOne
    @JoinColumn(name="member_id")
    private GroupMember groupMember;

    @Column(name="content")
    private String content;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
