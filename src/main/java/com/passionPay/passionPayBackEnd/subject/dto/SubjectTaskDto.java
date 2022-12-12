package com.passionPay.passionPayBackEnd.subject.dto;

import com.passionPay.passionPayBackEnd.task.domain.Task;
import com.passionPay.passionPayBackEnd.task.dto.TaskDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectTaskDto {
    private Long subjectId;
    private String subjectTitle;
    private List<TaskDto> tasks = new ArrayList<>();

    public boolean addTask(TaskDto taskDto) {
        if (taskDto == null) return false;
        return this.tasks.add(taskDto);
    }

    static public SubjectTaskDto from(Task task) {
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(TaskDto.from(task));

        return SubjectTaskDto.builder()
                .subjectId(task.getSubject().getSubjectId())
                .subjectTitle(task.getSubject().getTitle())
                .tasks(tasks)
                .build();
    }
}
