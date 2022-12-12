package com.passionPay.passionPayBackEnd.timestamp.repository;

import com.passionPay.passionPayBackEnd.planner.domain.Planner;
import com.passionPay.passionPayBackEnd.task.domain.Task;
import com.passionPay.passionPayBackEnd.timestamp.domain.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimestampRepository extends JpaRepository<Timestamp, Long> {
    Timestamp save(Timestamp timestamp);
    // timestamp list 저장
    // List<Timestamp> saveAll(List<Timestamp> timestamps);
    // task 와 연관된 timeStamp 조회
    List<Timestamp> findByTask(Task task);

    // timestamp 생성된 순서대로 조회
    @Query("SELECT ts " +
            "FROM Timestamp ts INNER JOIN ts.task t " +
            "WHERE t.planner = :planner " +
            "ORDER BY ts.timestampId")
    List<Timestamp> findByPlannerOrderById(@Param("planner") Planner planner);

    // timestamp 색상 업데이트
    /*
    @Modifying(clearAutomatically = true) // 영속성 컨텍스트 초기화
    @Query("UPDATE Timestamp t " +
            "SET t.color = :color " +
            "WHERE t.task = :task")
    int updateTimestampColorByTask(@Param("task") Task task, @Param("color") String color);
    */

    void delete(Timestamp timestamp);
    void deleteById(Long timestampId);
}
