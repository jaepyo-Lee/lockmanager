package com.ime.lockmanager.locker.application.port.in.req;

import com.ime.lockmanager.user.domain.UserState;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ModifyLockerInfoReqeustDto {
    private Long lockerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<UserState> userStates;
    private List<UserTier> userTiers;
    private String imageUrl;
    private String imageName;
}
