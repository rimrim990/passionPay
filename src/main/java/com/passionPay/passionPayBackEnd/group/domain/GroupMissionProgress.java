package com.passionPay.passionPayBackEnd.group.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name="GroupMissionProgress")
@Table(name="group_mission_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GroupMissionProgress {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="group_mission")
    private GroupMission groupMission;

    @Column(name="group_member_id", insertable = false, updatable = false)
    private Long groupMemberId;

    @ManyToOne
    @JoinColumn(name="group_member")
    private GroupMember groupMember;

    @Column(name="is_complete")
    private boolean complete;
}
