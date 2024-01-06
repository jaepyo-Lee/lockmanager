package com.ime.lockmanager.locker.adapter.in.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ime.lockmanager.locker.application.port.in.req.ModifyLockerInfoReqeustDto;
import com.ime.lockmanager.user.domain.UserState;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ModifyLockerInfoReqeust {
    private String lockerName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private List<UserState> userStates;
    private List<UserTier> userTiers;
    private String imageUrl;
    private String imageName;

    public ModifyLockerInfoReqeustDto toReqeustDto(Long lockerId) {
        return ModifyLockerInfoReqeustDto.builder()
                .lockerName(lockerName)
                .lockerId(lockerId)
                .startTime(startTime)
                .endTime(endTime)
                .imageName(imageName)
                .imageUrl(imageUrl)
                .userStates(userStates)
                .userTiers(userTiers)
                .build();
    }
}
