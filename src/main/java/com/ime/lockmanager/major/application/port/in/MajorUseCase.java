package com.ime.lockmanager.major.application.port.in;


import com.ime.lockmanager.major.application.port.in.req.CreateMajorRequestDto;
import com.ime.lockmanager.major.application.port.in.res.AllMajorInfoResponseDto;
import com.ime.lockmanager.major.application.port.in.res.CreateMajorResponseDto;
import com.ime.lockmanager.major.application.port.in.req.ModifyMajorNameReqeustDto;
import com.ime.lockmanager.major.application.port.in.res.ModifyMajorNameResponseDto;

import java.util.List;

public interface MajorUseCase {
    ModifyMajorNameResponseDto modifyMajorName(ModifyMajorNameReqeustDto modifyMajorNameReqeustDto);

    CreateMajorResponseDto createMajor(CreateMajorRequestDto createMajorRequestDto);

    List<AllMajorInfoResponseDto> findAll();
}
