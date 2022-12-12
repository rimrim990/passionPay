package com.passionPay.passionPayBackEnd.group.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name="GroupLike")
@Table(name="group_like", uniqueConstraints = @UniqueConstraint(columnNames = {"group_member_id", "group_post_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupLike {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="group_post_id")
    private GroupPost groupPost;

    @ManyToOne
    @JoinColumn(name="group_member_id")
    private GroupMember groupMember;
}
