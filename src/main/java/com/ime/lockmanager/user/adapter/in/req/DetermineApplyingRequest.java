package com.ime.lockmanager.user.adapter.in.req;

import com.ime.lockmanager.user.application.port.in.req.DetermineApplyingRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class DetermineApplyingRequest {
    @NotBlank
    @Schema(description = "승인을 받을 학생의 학번")
    private String studentNum;

    public DetermineApplyingRequestDto toRequestDto() {
        return DetermineApplyingRequestDto.builder()
                .studentNum(studentNum).build();
    }
}
