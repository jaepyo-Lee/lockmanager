package com.ime.lockmanager.major.application.port.in.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MajorDetailInMajorResponseDto {
    private String majorDetailName;
    private Long majorDetailId;
}
