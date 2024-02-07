package com.ime.lockmanager.locker.application.port.in.res;

import com.ime.lockmanager.locker.application.port.in.dto.CreatedLockerInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LeftLockerResponseDto {

    private List<CreatedLockerInfo> createdLockerInfo;
    public CreatedLockersInfoResponse toResponse(){
        return CreatedLockersInfoResponse.builder()
                .createdLockerInfo(createdLockerInfo)
                .build();
    }

}
