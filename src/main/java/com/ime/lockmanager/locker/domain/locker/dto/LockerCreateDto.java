package com.ime.lockmanager.locker.domain.locker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.domain.UserState;
import com.ime.lockmanager.user.domain.UserTier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class LockerCreateDto {
    private Major major;
    private String lockerName;
    private String totalRow;
    private String totalColumn;
    private String imageUrl;
    private String imageName;
    private List<UserState> userStates;
    private List<UserTier> userTiers;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startReservationTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endReservationTime;
}
