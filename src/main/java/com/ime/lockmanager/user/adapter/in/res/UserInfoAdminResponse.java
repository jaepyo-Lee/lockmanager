package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.UserTier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoAdminResponse {
    private UserInfo userInfo;
    private ReservationInfo reservationInfo;

}
