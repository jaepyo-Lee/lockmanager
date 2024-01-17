package com.ime.lockmanager.major.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMajorDetailRequestDto {
    private String majorDetailName;
    private Long majorId;
    public static CreateMajorDetailRequestDto of(String majorDetailName, Long majorId){
        return CreateMajorDetailRequestDto.builder()
                .majorDetailName(majorDetailName)
                .majorId(majorId)
                .build();
    }
}
