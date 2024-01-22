package com.ime.lockmanager.user.application.port.out.res;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.adapter.in.res.ReservationInfo;
import com.ime.lockmanager.user.adapter.in.res.UserInfo;
import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminResponse;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static com.ime.lockmanager.reservation.domain.ReservationStatus.RESERVED;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllUserInfoForAdminResponseDto {
    private String name;
    private UserTier userTier;
    private String status;
    private String studentNum;
    private Role role;
    private Long userId;

    private String lockerName;
    private String lockerNum;

    public AllUserInfoForAdminResponseDto(String name,
                                          UserTier userTier,
                                          String status,
                                          String studentNum,
                                          Role role,
                                          String lockerName,
                                          String lockerNum) {
        this.name = name;
        this.userTier = userTier;
        this.status = status;
        this.studentNum = studentNum;
        this.role = role;
        this.lockerNum = lockerNum;
        this.lockerName = lockerName;
    }

    public UserInfoAdminResponse toResponse() {
        return UserInfoAdminResponse.builder()
                .userInfo(UserInfo.builder()
                        .userId(userId)
                        .userTier(userTier)
                        .studentNum(studentNum)
                        .studentName(name)
                        .role(role)
                        .status(status)
                        .build())
                .reservationInfo(ReservationInfo.builder()
                        .lockerName(lockerName)
                        .lockerNum(lockerNum)
                        .build())
                .build();
    }

    public static AllUserInfoForAdminResponseDto of(User user, Optional<Reservation> reservation) {
        String lockerNum = reservation.map(r -> r.getLockerDetail().getLockerNum()).orElse(null);
        String lockerName = reservation.map(r -> r.getLockerDetail().getLocker().getName()).orElse(null);
        return AllUserInfoForAdminResponseDto.builder()
                .userId(user.getId())
                .studentNum(user.getStudentNum())
                .status(user.getStatus())
                .role(user.getRole())
                .userTier(user.getUserTier())
                .name(user.getName())
                .lockerName(lockerName)
                .lockerNum(lockerNum)
                .build();
    }
}
