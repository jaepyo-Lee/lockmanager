package com.ime.lockmanager.major.application.service;

import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.major.application.port.in.MajorUseCase;
import com.ime.lockmanager.major.application.port.in.req.ModifyMajorNameReqeustDto;
import com.ime.lockmanager.major.application.port.in.res.ModifyMajorNameResponseDto;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MajorService implements MajorUseCase {
    private final UserUseCase userUseCase;

    @Override
    public ModifyMajorNameResponseDto modifyMajorName(ModifyMajorNameReqeustDto modifyMajorNameReqeustDto) {
        User admin = userUseCase.findByStudentNumWithMajorDetailWithMajor(
                        modifyMajorNameReqeustDto.getAdminStudentNum())
                .orElseThrow(NotFoundUserException::new);
        String changeMajorName = changeMajorName(modifyMajorNameReqeustDto, admin.getMajorDetail().getMajor());
        return ModifyMajorNameResponseDto.of(changeMajorName);
    }

    private String changeMajorName(ModifyMajorNameReqeustDto modifyMajorNameReqeustDto, Major major) {
        return major.changeRepresentName(modifyMajorNameReqeustDto.getModifyMajorName());
    }
}