package com.ime.lockmanager.locker.application.port.in.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ime.lockmanager.locker.adapter.in.req.LockerCreateRequest;
import com.ime.lockmanager.locker.adapter.in.req.LockerDetailCreateRequest;
import com.ime.lockmanager.locker.domain.locker.dto.LockerCreateDto;
import com.ime.lockmanager.major.domain.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startReservationTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endReservationTime;
    private List<LockerDetailCreateRequest> lockerDetailCreateRequests;

    public static LockerCreateRequestDto fromRequestDto(LockerCreateRequest lockerCreateRequest){
        return LockerCreateRequestDto.of(lockerCreateRequest);
    }

    private static LockerCreateRequestDto of(LockerCreateRequest lockerCreateRequest) {
        return LockerCreateRequestDto.builder()
                .lockerName(lockerCreateRequest.getLockerName())
                .totalRow(lockerCreateRequest.getTotalRow())
                .totalColumn(lockerCreateRequest.getTotalColumn())
                .startReservationTime(lockerCreateRequest.getStartReservationTime())
                .endReservationTime(lockerCreateRequest.getEndReservationTime())
                .lockerDetailCreateRequests(lockerCreateRequest.getLockerDetailCreateRequests())
                .build();
    }

    public LockerCreateDto toLockerCreateDto(Major major){
        return LockerCreateDto.builder()
                .totalRow(this.getTotalRow())
                .totalColumn(this.getTotalColumn())
                .lockerName(this.getLockerName())
                .startReservationTime(this.getStartReservationTime())
                .endReservationTime(this.getEndReservationTime())
                .major(major)
                .build();
    }
}
