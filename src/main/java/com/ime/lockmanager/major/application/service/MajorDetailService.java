package com.ime.lockmanager.major.application.service;

import com.ime.lockmanager.common.format.exception.major.majordetail.DuplicatedMajorDetailException;
import com.ime.lockmanager.common.format.exception.major.majordetail.NotFoundMajorDetailException;
import com.ime.lockmanager.major.application.port.in.res.MajorDetailInMajorResponseDto;
import com.ime.lockmanager.major.application.port.in.MajorDetailUseCase;
import com.ime.lockmanager.major.application.port.in.req.CreateMajorDetailRequestDto;
import com.ime.lockmanager.major.application.port.out.major.MajorCommandPort;
import com.ime.lockmanager.major.application.port.out.majordetail.MajorDetailCommandPort;
import com.ime.lockmanager.major.application.port.out.majordetail.MajorDetailQueryPort;
import com.ime.lockmanager.major.application.port.out.major.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.major.domain.MajorDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MajorDetailService implements MajorDetailUseCase {
    private final MajorDetailQueryPort majorDetailQueryPort;
    private final MajorQueryPort majorQueryPort;
    private final MajorDetailCommandPort majorDetailCommandPort;

    @Override
    public Optional<MajorDetail> findByNameWithMajor(String majorDetailName) {
        return majorDetailQueryPort.findByNameWithMajor(majorDetailName);
    }

    @Override
    public List<MajorDetailInMajorResponseDto> findAllByMajorId(Long majorId) {
        List<MajorDetail> majorDetails = majorDetailQueryPort.findAllByMajorId(majorId);
        List<MajorDetailInMajorResponseDto> majorDetailInMajorResponseDtos = majorDetails.stream()
                .map(majorDetail -> MajorDetailInMajorResponseDto.builder()
                        .majorDetailId(majorId)
                        .majorDetailName(majorDetail.getName()).build())
                .collect(Collectors.toList());
        return majorDetailInMajorResponseDtos;
    }

    /**
     * @param createMajorDetailRequestDto
     * @return 생성된 소학과의 이름
     */
    @Override
    public String createMajorDetail(CreateMajorDetailRequestDto createMajorDetailRequestDto) {
        duplicatedMajorDetail(createMajorDetailRequestDto.getMajorDetailName());
        Major findMajor = majorQueryPort.findById(createMajorDetailRequestDto.getMajorId())
                .orElseThrow(NotFoundMajorDetailException::new);//예외처리 새로 만들어 해야함
        MajorDetail saveMajorDetail = majorDetailCommandPort.save(MajorDetail.builder()
                .name(createMajorDetailRequestDto.getMajorDetailName())
                .major(findMajor)
                .build());
        return saveMajorDetail.getName();
    }

    //중복 이름 찾기
    private void duplicatedMajorDetail(String majorDetailName) {
        List<MajorDetail> allMajorDetail = majorDetailQueryPort.findAll();
        for (MajorDetail majorDetail : allMajorDetail) {
            if (majorDetail.getName().equals(majorDetailName)) {
                throw new DuplicatedMajorDetailException();
            }
        }
    }
}
