package com.ime.lockmanager.reservation.adapter.out.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteReservationByStudentNumDto {
    private String studentNum;
}
