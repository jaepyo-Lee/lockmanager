package com.ime.lockmanager.major.application.port.in;

import com.ime.lockmanager.major.application.port.in.req.CreateMajorDetailRequestDto;
import com.ime.lockmanager.major.domain.MajorDetail;

import java.util.Optional;

public interface MajorDetailUseCase {
    Optional<MajorDetail> findMajorDetailByName(String majorDetailName);

    String createMajorDetail(String userStudentNum,CreateMajorDetailRequestDto createMajorDetailRequestDto);
}
