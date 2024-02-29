package com.ime.lockmanager.major.application.port.in.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllMajorInfoResponseDto {
    private String majorName;
    private Long majorId;
    public static AllMajorInfoResponseDto of(String majorName,Long majorId){
        return AllMajorInfoResponseDto.builder()
                .majorId(majorId)
                .majorName(majorName)
                .build();
    }
}
