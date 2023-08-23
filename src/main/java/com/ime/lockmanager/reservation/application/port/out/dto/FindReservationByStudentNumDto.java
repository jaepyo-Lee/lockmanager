package com.ime.lockmanager.reservation.application.port.out.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindReservationByStudentNumDto {
    private String studentNum;
}
