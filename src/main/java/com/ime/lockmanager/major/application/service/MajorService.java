package com.ime.lockmanager.major.application.service;

import com.ime.lockmanager.common.format.exception.major.majordetail.NotFoundMajorDetailException;
import com.ime.lockmanager.major.application.port.in.MajorDetailUseCase;
import com.ime.lockmanager.major.application.port.in.MajorUseCase;
import com.ime.lockmanager.major.application.port.in.req.CreateMajorDetailRequestDto;
import com.ime.lockmanager.major.application.port.in.req.CreateMajorRequestDto;
import com.ime.lockmanager.major.application.port.in.req.ModifyMajorNameReqeustDto;
import com.ime.lockmanager.major.application.port.in.res.AllMajorInfoResponseDto;
import com.ime.lockmanager.major.application.port.in.res.CreateMajorResponseDto;
import com.ime.lockmanager.major.application.port.in.res.ModifyMajorNameResponseDto;
import com.ime.lockmanager.major.application.port.out.MajorDetailQueryPort;
import com.ime.lockmanager.major.application.port.out.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MajorService implements MajorUseCase {
    private final MajorQueryPort majorQueryPort;
    private final MajorDetailUseCase majorDetailUseCase;

    @Override
    public List<AllMajorInfoResponseDto> findAll() {
        List<Major> allMajor = majorQueryPort.findAll();
        return allMajor.stream()
                .map(major -> AllMajorInfoResponseDto.of(major.getName(), major.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public ModifyMajorNameResponseDto modifyMajorName(ModifyMajorNameReqeustDto modifyMajorNameReqeustDto) {
        Major major = majorQueryPort.findById(modifyMajorNameReqeustDto.getMajorId())
                .orElseThrow(NotFoundMajorDetailException::new);//예외처리해야함
        String changeMajorName = major.changeName(modifyMajorNameReqeustDto.getModifyMajorName());
        return ModifyMajorNameResponseDto.of(changeMajorName);
    }

    @Override
    public CreateMajorResponseDto createMajor(CreateMajorRequestDto createMajorRequestDto) {
        Major savedMajor = majorQueryPort.save(Major.of(createMajorRequestDto.getMajorName()));
        majorDetailUseCase.createMajorDetail(CreateMajorDetailRequestDto
                .of(createMajorRequestDto.getMajorName(), savedMajor.getId()));
        return CreateMajorResponseDto.of(savedMajor.getName(), savedMajor.getId());
    }
}