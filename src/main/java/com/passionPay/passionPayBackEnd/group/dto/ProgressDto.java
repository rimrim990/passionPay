package com.passionPay.passionPayBackEnd.group.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressDto {
    private int timeProgress;
    private int missionProgress;
}
