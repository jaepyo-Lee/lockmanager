package com.ime.lockmanager.major.adapter.in.majordetail.req;

import com.ime.lockmanager.major.application.port.in.req.CreateMajorDetailRequestDto;
import lombok.Getter;

@Getter
public class CreateMajorDetailRequest {
    private String majorDetailName;

    public CreateMajorDetailRequestDto toRequestDto(){
        return CreateMajorDetailRequestDto.builder()
                .majorDetailName(majorDetailName)
                .build();
    }
}
