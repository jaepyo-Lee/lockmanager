package com.ime.lockmanager.major.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyMajorNameReqeustDto {
    private String modifyMajorName;
    private Long majorId;

    public static ModifyMajorNameReqeustDto of(String modifyMajorName,Long majorId){
        return ModifyMajorNameReqeustDto.builder()
                .modifyMajorName(modifyMajorName)
                .majorId(majorId)
                .build();
    }
}
