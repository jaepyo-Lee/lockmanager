package com.ime.lockmanager.locker.application.port.in.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ime.lockmanager.locker.adapter.in.req.LockerCreateRequest;
import com.ime.lockmanager.locker.adapter.in.req.LockerDetailCreateRequest;
import com.ime.lockmanager.locker.adapter.in.req.NumberIncreaseDirection;
import com.ime.lockmanager.locker.domain.ImageInfo;
import com.ime.lockmanager.locker.domain.locker.dto.LockerCreateDto;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.domain.UserState;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LockerCreateRequestDto {
    private String lockerName;
    private String totalRow;
    private String totalColumn;
    private MultipartFile image;
    private List<UserState> userStates;
    private List<UserTier> userTiers;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startReservationTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endReservationTime;
    //    private List<LockerDetailCreateRequest> lockerDetailCreateRequests;
    private NumberIncreaseDirection numberIncreaseDirection;
    public static LockerCreateRequestDto fromRequest(LockerCreateRequest lockerCreateRequest,MultipartFile image) {
        return LockerCreateRequestDto.builder()
                .lockerName(lockerCreateRequest.getLockerName())
                .totalRow(lockerCreateRequest.getTotalRow())
                .totalColumn(lockerCreateRequest.getTotalColumn())
                .startReservationTime(lockerCreateRequest.getStartReservationTime())
                .endReservationTime(lockerCreateRequest.getEndReservationTime())
                .image(image)
                .userTiers(lockerCreateRequest.getUserTiers())
                .userStates(lockerCreateRequest.getUserStates())
                .numberIncreaseDirection(lockerCreateRequest.getNumberIncreaseDirection())
                .build();
    }

    public LockerCreateDto toLockerCreateDto(Major major,String imageUrl) {
        return LockerCreateDto.builder()
                .totalRow(this.getTotalRow())
                .totalColumn(this.getTotalColumn())
                .lockerName(this.getLockerName())
                .userStates(this.userStates)
                .userTiers(this.userTiers)
                .startReservationTime(this.getStartReservationTime())
                .endReservationTime(this.getEndReservationTime())
                .major(major)
                .imageUrl(imageUrl)
                .build();
    }
}
