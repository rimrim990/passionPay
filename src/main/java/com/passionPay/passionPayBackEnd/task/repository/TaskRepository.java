package com.passionPay.passionPayBackEnd.task.repository;

import com.passionPay.passionPayBackEnd.task.dto.TaskDto;
import com.passionPay.passionPayBackEnd.planner.domain.Planner;
import com.passionPay.passionPayBackEnd.subject.domain.Subject;
import com.passionPay.passionPayBackEnd.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // task 저장
    Task save(Task task);

    // task 조회
    Optional<Task> findByTaskId(Long taskId);
    List<Task> findBySubject(Subject subject);

    @Query("SELECT new com.passionPay.passionPayBackEnd.task.dto.TaskDto(t.taskId, " +
            "t.taskName, t.status, t.totalTime, t.color) " +
            "FROM Task t INNER JOIN t.planner " +
            "WHERE t.planner = :planner")
    List<TaskDto> findByPlanner(@Param("planner") Planner planner);

    @Query("SELECT SUM(t.totalTime) " +
            "FROM Task t INNER JOIN t.planner " +
            "WHERE t.planner = :planner")
    int findTotalTimeByPlanner(@Param("planner") Planner planner);

    // task 삭제
    void deleteByTaskId(Long taskId);
    void delete(Task task);
}
