package com.ime.lockmanager.major.adapter.in.major.res;

import com.ime.lockmanager.major.application.port.in.res.ModifyMajorNameResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "수정된 학과 응답 dto")
@Builder
@Getter
public class ModifyMajorNameResponse {
    @Schema(description = "수정된 학과명")
    private String changedMajorName;
    public static ModifyMajorNameResponse fromResponseDto(ModifyMajorNameResponseDto modifyMajorNameResponseDto){
        return ModifyMajorNameResponse.builder()
                .changedMajorName(modifyMajorNameResponseDto.getChangedMajorName())
                .build();
    }
}
