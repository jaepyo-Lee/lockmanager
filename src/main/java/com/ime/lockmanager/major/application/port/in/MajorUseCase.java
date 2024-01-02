package com.ime.lockmanager.major.application.port.in;


import com.ime.lockmanager.major.application.port.in.req.ModifyMajorNameReqeustDto;
import com.ime.lockmanager.major.application.port.in.res.ModifyMajorNameResponseDto;

public interface MajorUseCase {
    ModifyMajorNameResponseDto modifyMajorName(ModifyMajorNameReqeustDto modifyMajorNameReqeustDto);
}
