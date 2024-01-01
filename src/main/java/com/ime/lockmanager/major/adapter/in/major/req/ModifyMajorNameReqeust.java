package com.ime.lockmanager.major.adapter.in.major.req;

import com.ime.lockmanager.major.application.port.in.req.ModifyMajorNameReqeustDto;
import lombok.Getter;

@Getter
public class ModifyMajorNameReqeust {
    private String modifyMajorName;
    public ModifyMajorNameReqeustDto toRequestDto(String adminStudentNum){
        return ModifyMajorNameReqeustDto.builder()
                .modifyMajorName(modifyMajorName)
                .adminStudentNum(adminStudentNum)
                .build();
    }
}
