package com.ime.lockmanager.major.application.port.in.res;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class ModifyMajorNameResponseDto {
    private String changedMajorName;
    public static ModifyMajorNameResponseDto of(String changedMajorName){
        return ModifyMajorNameResponseDto.builder()
                .changedMajorName(changedMajorName)
                .build();
    }

}
