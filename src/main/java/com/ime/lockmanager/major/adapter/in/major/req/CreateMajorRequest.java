package com.ime.lockmanager.major.adapter.in.major.req;

import com.ime.lockmanager.major.application.port.in.req.CreateMajorRequestDto;
import lombok.Getter;

@Getter
public class CreateMajorRequest {
    private String majorName;
    public CreateMajorRequestDto toRequestDto(){
        return CreateMajorRequestDto.of(majorName);
    }
}
