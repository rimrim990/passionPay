package com.passionPay.passionPayBackEnd.task.dto;

import com.passionPay.passionPayBackEnd.planner.domain.Planner;
import com.passionPay.passionPayBackEnd.subject.domain.Subject;
import com.passionPay.passionPayBackEnd.task.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    private String subjectTitle;
    private String taskName;
    private int status;
    private String color;

    public static Task from (TaskRequestDto taskRequestDto, Planner planner, Subject subject) {
        return Task.builder()
                .taskName(taskRequestDto.getTaskName())
                .status(taskRequestDto.getStatus())
                .planner(planner)
                .subject(subject)
                .color(taskRequestDto.getColor())
                .build();
    }
}
