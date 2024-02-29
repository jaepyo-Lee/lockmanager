package com.ime.lockmanager.major.application.port.in;

import com.ime.lockmanager.major.application.port.in.req.CreateMajorDetailRequestDto;
import com.ime.lockmanager.major.application.port.in.res.MajorDetailInMajorResponseDto;
import com.ime.lockmanager.major.domain.MajorDetail;

import java.util.List;
import java.util.Optional;

public interface MajorDetailUseCase {
    Optional<MajorDetail> findByNameWithMajor(String majorDetailName);

    String createMajorDetail(CreateMajorDetailRequestDto createMajorDetailRequestDto);

    List<MajorDetailInMajorResponseDto> findAllByMajorId(Long majorId);
}
