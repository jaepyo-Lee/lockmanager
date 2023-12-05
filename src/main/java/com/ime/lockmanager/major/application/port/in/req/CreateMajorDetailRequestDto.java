package com.ime.lockmanager.major.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMajorDetailRequestDto {
    private String majorDetailName;
}
