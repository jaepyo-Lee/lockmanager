package com.ime.lockmanager.user.domain.dto;

import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.Role;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UpdateUserInfoDto {
    private String status;
    private String grade;
    private String major;
    private boolean auth;
}
