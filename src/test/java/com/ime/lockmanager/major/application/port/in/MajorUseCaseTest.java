package com.ime.lockmanager.major.application.port.in;

import com.ime.lockmanager.common.format.exception.major.majordetail.NotFoundMajorDetailException;
import com.ime.lockmanager.major.application.port.in.req.CreateMajorRequestDto;
import com.ime.lockmanager.major.application.port.in.res.CreateMajorResponseDto;
import com.ime.lockmanager.major.application.port.out.MajorDetailQueryPort;
import com.ime.lockmanager.major.application.port.out.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.major.domain.MajorDetail;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class MajorUseCaseTest {
    @Autowired
    UserQueryPort userQueryPort;
    @Autowired
    MajorUseCase majorUseCase;

    @Autowired
    MajorDetailQueryPort majorDetailQueryPort;
    @Autowired
    MajorQueryPort majorQueryPort;

    @DisplayName("학과 생성 api 테스트")
    @Test
    void createMajor() {
        //given
        CreateMajorRequestDto createMajorRequestDto = CreateMajorRequestDto.of("test");
        //when
        CreateMajorResponseDto createMajorResponseDto = majorUseCase.createMajor(createMajorRequestDto); //학과 생성
        //then
        Major major = majorQueryPort.findByName(createMajorResponseDto.getMajorName()).orElseThrow(NotFoundMajorDetailException::new);
        MajorDetail majorDetail = majorDetailQueryPort.findByNameWithMajor(createMajorResponseDto.getMajorName())
                .orElseThrow(NotFoundMajorDetailException::new);
        assertAll(
                ()->assertEquals(createMajorRequestDto.getMajorName(),"test"),
                ()->assertEquals(createMajorResponseDto.getMajorName(),"test"),
                ()->assertEquals(major.getName(),"test"),
                ()->assertEquals(majorDetail.getName(),"test")
        );
    }
}