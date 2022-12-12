package com.passionPay.passionPayBackEnd.group.domain;

import com.passionPay.passionPayBackEnd.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="group_member", uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "member_id"}))
@Entity(name="GroupMember")
public class GroupMember {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Group group;

    @Column(name="group_id", updatable = false, insertable = false)
    private Long groupId;

    @ManyToOne
    private Member member;

    @Column(name="member_id", updatable = false, insertable = false)
    private Long memberId;
}
