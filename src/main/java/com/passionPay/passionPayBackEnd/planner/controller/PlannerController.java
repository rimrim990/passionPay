package com.passionPay.passionPayBackEnd.planner.controller;

import com.passionPay.passionPayBackEnd.planner.dto.PlannerDto;
import com.passionPay.passionPayBackEnd.planner.payload.CreatePlannerPayload;
import com.passionPay.passionPayBackEnd.planner.domain.Planner;
import com.passionPay.passionPayBackEnd.planner.service.PlannerService;
import com.passionPay.passionPayBackEnd.util.DateUtil;
import com.passionPay.passionPayBackEnd.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/planner")
public class PlannerController {
    private final PlannerService plannerService;

    @Autowired
    public PlannerController(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    // 플래너 생성
    @PostMapping("/{memberId}")
    public ResponseEntity<Planner> savePlanner(@PathVariable(name="memberId") Long memberId, @RequestBody CreatePlannerPayload createPlannerPayload) {
        System.out.println(createPlannerPayload.toString());

        Planner planner = Planner.builder()
                .memberId(memberId)
                .date(DateUtil.parseStringToDate(createPlannerPayload.getDate()))
                .dDay(DateUtil.parseStringToDate(createPlannerPayload.getDDay()))
                .comment(createPlannerPayload.getComment())
                .expectedStudyTime(DateUtil.parseStringToTime(createPlannerPayload.getExpectedStudyTime()))
                .currentStudyTime(LocalTime.MIN)
                .likeCount(0)
                .build();
        return ResponseEntity.ok(plannerService.savePlanner(planner));
    }

    // member_id로 플래너 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<Planner>> getPlannerByMemberId(
            @PathVariable(name="memberId") Long memberId) {
        return ResponseEntity.ok(plannerService.getPlannerByMemberId(memberId));
    }

    // member_id 와 date 로 플래너 조회
    @GetMapping("/{memberId}/date/{date}")
    public ResponseEntity<PlannerDto> getPlannerByMemberIdAndDate(
            @PathVariable(name="memberId") Long memberId, @PathVariable(name="date") String date) {
        return ResponseEntity.ok(plannerService.getPlannerByMemberIdAndDate(memberId, DateUtil.parseStringToDate(date)));
    }

    @PutMapping("/{plannerId}")
    public ResponseEntity<Planner> updatePlanner(
            @PathVariable(name="plannerId") Long plannerId, @RequestBody CreatePlannerPayload createPlannerPayload) {
        Planner planner = plannerService.getPlannerById(plannerId);
        if (planner == null) throw new RuntimeException("유효하지 않은 플래너");
        if (planner.getMemberId() != SecurityUtil.getCurrentMemberId()) throw new RuntimeException("접근 권한 없음");
        return ResponseEntity.ok(plannerService.updatePlanner(createPlannerPayload, planner));
    }

    // planner_id로 플래너 삭제
    @DeleteMapping("/{plannerId}")
    public ResponseEntity<Boolean> deletePlannerById(@PathVariable(name="plannerId") Long plannerId) {
        return ResponseEntity.ok(plannerService.deletePlanner(plannerId));
    }

}
