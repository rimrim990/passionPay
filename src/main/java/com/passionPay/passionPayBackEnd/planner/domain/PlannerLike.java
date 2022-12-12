package com.passionPay.passionPayBackEnd.planner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.passionPay.passionPayBackEnd.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity(name="PlannerLike")
@Table(name="planner_like", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "planner_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannerLike {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_id", insertable = false, updatable = false)
    private Long memberId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Member member;

    @ManyToOne
    @JoinColumn(name="planner_id")
    private Planner planner;
}
