package com.ime.lockmanager.locker.application.port.in.res;

import com.ime.lockmanager.locker.application.port.in.dto.LeftLockerInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LeftLockerResponseDto {

    private List<LeftLockerInfo> leftLockerInfo;
    public LeftLockerResponse toResponse(){
        return LeftLockerResponse.builder()
                .leftLockerInfo(leftLockerInfo)
                .build();
    }

}
