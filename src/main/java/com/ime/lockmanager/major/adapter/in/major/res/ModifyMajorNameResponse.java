package com.ime.lockmanager.major.adapter.in.major.res;

import com.ime.lockmanager.major.application.port.in.res.ModifyMajorNameResponseDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModifyMajorNameResponse {
    private String changedMajorName;
    public static ModifyMajorNameResponse fromResponseDto(ModifyMajorNameResponseDto modifyMajorNameResponseDto){
        return ModifyMajorNameResponse.builder()
                .changedMajorName(modifyMajorNameResponseDto.getChangedMajorName())
                .build();
    }
}
