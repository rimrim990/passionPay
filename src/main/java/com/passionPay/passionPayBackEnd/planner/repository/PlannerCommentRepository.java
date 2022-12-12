package com.passionPay.passionPayBackEnd.planner.repository;

import com.passionPay.passionPayBackEnd.planner.domain.Planner;
import com.passionPay.passionPayBackEnd.planner.domain.PlannerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerCommentRepository extends JpaRepository<PlannerComment, Long> {
    //save
    PlannerComment save(PlannerComment plannerComment);

    // find
    Optional<PlannerComment> findById(Long plannerCommentId);
    List<PlannerComment> findByPlanner(Planner planner);

    // delete
    void delete(PlannerComment plannerComment);
    void deleteById(Long plannerCommentId);
}
