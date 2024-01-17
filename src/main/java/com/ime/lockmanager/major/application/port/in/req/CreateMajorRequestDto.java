package com.ime.lockmanager.major.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateMajorRequestDto {
    private String majorName;

    public static CreateMajorRequestDto of(String majorName) {
        return CreateMajorRequestDto.builder()
                .majorName(majorName)
                .build();
    }

}
