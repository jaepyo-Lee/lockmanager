package com.ime.lockmanager.major.application.port.in.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateMajorResponseDto {
    private String majorName;
    private Long majorId;
    public static CreateMajorResponseDto of(String majorName,Long majorId){
        return CreateMajorResponseDto.builder()
                .majorName(majorName)
                .majorId(majorId)
                .build();
    }

}
