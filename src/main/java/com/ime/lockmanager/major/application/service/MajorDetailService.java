package com.ime.lockmanager.major.application.service;

import com.ime.lockmanager.common.format.exception.major.majordetail.DuplicatedMajorDetailException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.major.application.port.in.MajorDetailUseCase;
import com.ime.lockmanager.major.application.port.in.req.CreateMajorDetailRequestDto;
import com.ime.lockmanager.major.application.port.out.MajorDetailQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.major.domain.MajorDetail;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MajorDetailService implements MajorDetailUseCase {
    private final MajorDetailQueryPort majorDetailQueryPort;
    private final UserUseCase userUseCase;

    @Override
    public Optional<MajorDetail> findByNameWithMajor(String majorDetailName) {
        return majorDetailQueryPort.findByNameWithMajor(majorDetailName);
    }

    @Override
    public String createMajorDetail(String userStudentNum, CreateMajorDetailRequestDto createMajorDetailRequestDto) {
        User user = userUseCase.findByStudentNumWithMajorDetailWithMajor(userStudentNum)
                .orElseThrow(NotFoundUserException::new);
        Major representMajor = user.getMajorDetail().getMajor();
        duplicatedMajorDetail(createMajorDetailRequestDto.getMajorDetailName());

        MajorDetail saveMajorDetail = majorDetailQueryPort.save(MajorDetail.builder()
                .name(createMajorDetailRequestDto.getMajorDetailName())
                .major(representMajor)
                .build());

        return saveMajorDetail.getName();
    }

    private void duplicatedMajorDetail(String majorDetailName) {
        List<MajorDetail> allMajorDetail = majorDetailQueryPort.findAll();
        for (MajorDetail majorDetail : allMajorDetail) {
            if (majorDetail.getName().equals(majorDetailName)) {
                throw new DuplicatedMajorDetailException();
            }
        }
    }


}
