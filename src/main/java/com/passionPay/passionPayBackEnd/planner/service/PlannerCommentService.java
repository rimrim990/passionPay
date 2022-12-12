package com.passionPay.passionPayBackEnd.planner.service;

import com.passionPay.passionPayBackEnd.planner.dto.PlannerCommentDto;
import com.passionPay.passionPayBackEnd.planner.domain.PlannerComment;
import com.passionPay.passionPayBackEnd.member.repository.MemberRepository;
import com.passionPay.passionPayBackEnd.planner.repository.PlannerCommentRepository;
import com.passionPay.passionPayBackEnd.planner.repository.PlannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlannerCommentService {

    private final PlannerCommentRepository plannerCommentRepository;
    private final PlannerRepository plannerRepository;
    private final MemberRepository memberRepository;

    public PlannerCommentService(PlannerCommentRepository plannerCommentRepository, PlannerRepository plannerRepository, MemberRepository memberRepository) {
        this.plannerCommentRepository = plannerCommentRepository;
        this.plannerRepository = plannerRepository;
        this.memberRepository = memberRepository;
    }

    // 방명록 작성
    public PlannerCommentDto save(Long plannerId, String content, Long memberId) {
        return plannerRepository.findByPlannerId(plannerId)
                .map(planner -> memberRepository.findById(memberId)
                        .map(member -> {
                                PlannerComment plannerComment = PlannerComment.builder()
                                        .planner(planner)
                                        .member(member)
                                        .content(content)
                                        .build();
                                return PlannerCommentDto.from(plannerComment);
                            }).orElseThrow(RuntimeException::new))
                .orElseThrow(RuntimeException::new);
    }

    // 방명록 조회
    public List<PlannerCommentDto> getByPlannerId(Long plannerId) {
        return plannerRepository.findByPlannerId(plannerId)
                .map(planner ->
                    plannerCommentRepository.findByPlanner(planner)
                            .stream()
                            .map(plannerComment -> PlannerCommentDto.from(plannerComment))
                            .collect(Collectors.toList()))
                .orElseThrow(RuntimeException::new);
    }

    // 방명록 삭제
    public boolean deleteByPlannerCommentId(Long plannerCommentId) {
        return plannerCommentRepository.findById(plannerCommentId)
                .map(plannerComment -> {
                    plannerCommentRepository.delete(plannerComment);
                    return true;
                }).orElse(false);
    }
}
