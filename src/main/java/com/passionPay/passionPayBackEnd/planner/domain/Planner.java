package com.passionPay.passionPayBackEnd.planner.domain;

import com.passionPay.passionPayBackEnd.task.domain.Task;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity(name="Planner")
@Table(name="planner", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "date"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Planner {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plannerId;

    @Column(name="member_id", nullable=false)
    private Long memberId;

    @Column(name="date", nullable=false)
    private LocalDate date;

    @Column(name="d_day")
    private LocalDate dDay;

    @OneToMany(cascade=CascadeType.REMOVE)
    @Column(name="tasks")
    private List<Task> tasks;

    @Column(name="current_study_time")
    private LocalTime currentStudyTime;

    @Column(name="expected_study_time")
    private LocalTime expectedStudyTime;

    @Column(name="like_count")
    private int likeCount;

    @Column(name="comment", nullable=false)
    private String comment;

    @Column(name="evaluation")
    private String evaluation;

    public boolean addTask(Task task) {
        return this.tasks.add(task);
    }

    public boolean deleteTask(Task task) {
        return this.tasks.remove(task);
    }
}
