package com.ime.lockmanager.user.adapter.in.req;

import com.ime.lockmanager.user.application.port.in.req.DetermineApplyingRequestDto;
import lombok.Getter;

@Getter
public class DetermineApplyingRequest {
    private String studentNum;

    public DetermineApplyingRequestDto toRequestDto() {
        return DetermineApplyingRequestDto.builder()
                .studentNum(studentNum).build();
    }
}
