package com.ime.lockmanager.user.domain.dto;

import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.major.domain.MajorDetail;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.UserState;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UpdateUserInfoDto {
    private UserState status;
    private String grade;
    private MajorDetail majorDetail;
    private boolean auth;
}
