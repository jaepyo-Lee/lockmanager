package com.ime.lockmanager.major.adapter.in.majordetail.res;

import com.ime.lockmanager.major.application.port.in.res.MajorDetailInMajorResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MajorDetailInMajorResponse {
    List<MajorDetailInMajorResponseDto> majordetails;
}
