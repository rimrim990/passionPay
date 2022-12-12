package com.passionPay.passionPayBackEnd.planner.payload;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlannerPayload {
    private String date;
    private String expectedStudyTime;
    private String currentStudyTime;
    private String comment;
    private String evaluation;
    private String dDay;
}
