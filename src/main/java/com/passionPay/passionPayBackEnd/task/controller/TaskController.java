package com.passionPay.passionPayBackEnd.task.controller;

import com.passionPay.passionPayBackEnd.task.dto.TaskDto;
import com.passionPay.passionPayBackEnd.task.dto.TaskRequestDto;
import com.passionPay.passionPayBackEnd.planner.domain.Planner;
import com.passionPay.passionPayBackEnd.subject.domain.Subject;
import com.passionPay.passionPayBackEnd.task.domain.Task;
import com.passionPay.passionPayBackEnd.planner.service.PlannerService;
import com.passionPay.passionPayBackEnd.subject.service.SubjectService;
import com.passionPay.passionPayBackEnd.task.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planner")
public class TaskController {

    private final TaskService taskService;
    private final PlannerService plannerService;
    private final SubjectService subjectService;

    public TaskController(TaskService taskService, PlannerService plannerService, SubjectService subjectService) {
        this.taskService = taskService;
        this.plannerService = plannerService;
        this.subjectService = subjectService;
    }

    // planner 와 연관된 task 조회
    @GetMapping("/{plannerId}/task")
    public ResponseEntity<List<TaskDto>> getTaskByPlannerId(
            @PathVariable(name="plannerId") Long plannerId) {
        return ResponseEntity.ok(taskService.getTaskByPlanner(plannerId));
    }

    // task 추가
    @PostMapping("/{plannerId}/task")
    public ResponseEntity<TaskDto> saveTask(
            @PathVariable(name="plannerId") Long plannerId, @RequestBody TaskRequestDto taskRequestDto) {
        Planner planner = plannerService.getPlannerById(plannerId);
        // planner id 가 유효한지 검사
        if (planner == null) throw new RuntimeException("유효하지 않은 플래너");
        // planner 접근권한 확인
        // if (planner.getMemberId() != SecurityUtil.getCurrentMemberId())
        //     throw new RuntimeException("허용되지 않은 접근");

        Subject subject = subjectService.getSubjectByTitle(taskRequestDto.getSubjectTitle());
        if (subject == null) throw new RuntimeException("잘못된 접근입니다.");

        Task task = TaskRequestDto.from(taskRequestDto, planner, subject);
        taskService.saveTask(task);
        plannerService.addTaskToPlanner(planner, task);

        return ResponseEntity.ok(TaskDto.from(task));
    }

    // 태스크 업데이트
    @PutMapping("/task/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable(name="taskId") Long taskId, @RequestBody TaskRequestDto taskRequestDto
    ) {
        Task task = taskService.getTaskById(taskId);
        if (task == null) throw new RuntimeException("유효하지 않은 태스크");
        task.setStatus(taskRequestDto.getStatus());
        task.setTaskName(taskRequestDto.getTaskName());
        task.setColor(taskRequestDto.getColor());

        // task 과목 수정
        if (task.getSubject().getTitle() != taskRequestDto.getSubjectTitle()) {
            Subject subject = subjectService.getSubjectByTitle(taskRequestDto.getSubjectTitle());
            if (subject == null) throw new RuntimeException("유효하지 않은 과목명");
            task.setSubject(subject);
        }

        return ResponseEntity.ok(taskService.saveTask(task));
    }

    // 태스크 삭제
    @DeleteMapping("/{plannerId}/task/{taskId}")
    public ResponseEntity<Boolean> deleteTaskById(
            @PathVariable(name="plannerId") Long plannerId, @PathVariable(name="taskId") Long taskId) {
        Planner planner = plannerService.getPlannerById(plannerId);
        if (planner == null) throw new RuntimeException("유효하지 않은 플래너");

        Task task = taskService.getTaskById(taskId);
        if (task == null) throw new RuntimeException("유효하지 않은 태스크");

        // planner - task 연관관계 제거
        plannerService.deleteTaskFromPlanner(planner, task);
        return ResponseEntity.ok(taskService.deleteTask(task));
    }
}
