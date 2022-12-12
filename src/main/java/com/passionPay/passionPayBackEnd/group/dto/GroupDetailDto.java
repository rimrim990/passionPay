package com.passionPay.passionPayBackEnd.group.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDetailDto {
    private List<MyGroupDto> myGroupDto;
    private List<OtherGroupDto> otherGroupDto;
}
