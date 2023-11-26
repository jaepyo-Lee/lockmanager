package com.ime.lockmanager.reservation.adapter.in.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CancelLockerDetailResponse {
    private Long canceledLockerDetailNum;
    private String studentNum;
}
