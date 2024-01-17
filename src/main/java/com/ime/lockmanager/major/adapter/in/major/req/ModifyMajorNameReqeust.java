package com.ime.lockmanager.major.adapter.in.major.req;

import com.ime.lockmanager.major.application.port.in.req.ModifyMajorNameReqeustDto;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ModifyMajorNameReqeust {
    @NotBlank(message = "변경할 학과명은 필수입니다.")
    private String modifyMajorName;
    public ModifyMajorNameReqeustDto toRequestDto(Long majorId){
        return ModifyMajorNameReqeustDto.of(this.modifyMajorName, majorId);
    }
}
