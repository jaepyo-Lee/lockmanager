package com.ime.lockmanager.major.application.port.in;

import com.ime.lockmanager.common.format.exception.major.majordetail.NotFoundMajorDetailException;
import com.ime.lockmanager.major.application.port.in.req.CreateMajorRequestDto;
import com.ime.lockmanager.major.application.port.in.req.ModifyMajorNameReqeustDto;
import com.ime.lockmanager.major.application.port.in.res.AllMajorInfoResponseDto;
import com.ime.lockmanager.major.application.port.in.res.CreateMajorResponseDto;
import com.ime.lockmanager.major.application.port.in.res.ModifyMajorNameResponseDto;
import com.ime.lockmanager.major.application.port.out.MajorDetailQueryPort;
import com.ime.lockmanager.major.application.port.out.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.major.domain.MajorDetail;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
    @BeforeEach
    void init(){
      majorDetailQueryPort.deleteAll();
      majorQueryPort.deleteAll();
    }

    @Test
    void 전체학과_조회_테스트(){
        //given
        CreateMajorResponseDto test1 = createMajor("test1");
        CreateMajorResponseDto test2 = createMajor("test2");
        CreateMajorResponseDto test3 = createMajor("test3");
        //when
        List<AllMajorInfoResponseDto> allMajor = majorUseCase.findAll();
        //then
        assertAll(
                () -> assertEquals(allMajor.size(), 3)
        );
    }

    private CreateMajorResponseDto createMajor(String majorName) {
        CreateMajorRequestDto createMajorRequestDto = CreateMajorRequestDto.of(majorName);
        return majorUseCase.createMajor(createMajorRequestDto); //학과 생성
    }

    @Test
    void 학과_이름_수정_테스트() {
        //given
        CreateMajorResponseDto createMajorResponseDto = createMajor("test");
        //when
        ModifyMajorNameResponseDto modifyTest = majorUseCase.modifyMajorName(ModifyMajorNameReqeustDto
                .of("modifyTest", createMajorResponseDto.getMajorId()));
        //then
        assertAll(
                () -> assertEquals(modifyTest.getChangedMajorName(), "modifyTest")
        );
    }

    @Test
    void 존재하지_않은_학과_이름_수정_예외테스트() {
        //given
        CreateMajorResponseDto createMajorResponseDto = createMajor("test");
        //when
        //then
        assertThrows(NotFoundMajorDetailException.class,()->
                majorUseCase.modifyMajorName(ModifyMajorNameReqeustDto
                        .of("modifyExceptionTest", createMajorResponseDto.getMajorId()+1))
        );
    }

    @Test
    void 학과_생성_테스트() {
        //given
        CreateMajorRequestDto createMajorRequestDto = CreateMajorRequestDto.of("test");
        //when
        CreateMajorResponseDto createMajorResponseDto = majorUseCase.createMajor(createMajorRequestDto); //학과 생성
        //then
        Major major = majorQueryPort.findByName(createMajorResponseDto.getMajorName()).orElseThrow(NotFoundMajorDetailException::new);
        MajorDetail majorDetail = majorDetailQueryPort.findByNameWithMajor(createMajorResponseDto.getMajorName())
                .orElseThrow(NotFoundMajorDetailException::new);
        assertAll(
                () -> assertEquals(createMajorRequestDto.getMajorName(), "test"),
                () -> assertEquals(createMajorResponseDto.getMajorName(), "test"),
                () -> assertEquals(major.getName(), "test"),
                () -> assertEquals(majorDetail.getName(), "test")
        );
    }
}