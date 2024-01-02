package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.common.format.exception.locker.NotFoundLockerException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.LockerDetailUseCase;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.dto.LeftLockerInfo;
import com.ime.lockmanager.locker.application.port.in.req.FindAllLockerInMajorRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerCreateRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.ModifyLockerInfoReqeustDto;
import com.ime.lockmanager.locker.application.port.in.res.AllLockersInMajorResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LeftLockerResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerCreateResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.ImageInfo;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailInfo;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.domain.User;
import com.ime.lockmanager.user.domain.UserState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ime.lockmanager.locker.domain.lockerdetail.LockerDetailStatus.NON_RESERVED;
import static com.ime.lockmanager.locker.domain.lockerdetail.LockerDetailStatus.RESERVED;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
class LockerService implements LockerUseCase {
    private final LockerQueryPort lockerQueryPort;
    private final LockerDetailUseCase lockerDetailUseCase;
    private final UserUseCase userUseCase;

    //남은 사물함 목록
    @Override
    public LeftLockerResponseDto getLeftLocker(String studentNum) {
        User user = userUseCase.findByStudentNumWithMajorDetailWithMajor(studentNum)
                .orElseThrow(NotFoundUserException::new);
        Major major = user.getMajorDetail().getMajor();
        List<LeftLockerInfo> leftLockerInfos = lockerQueryPort.findLockerByUserMajor(major).stream()
                .map(locker -> {
                    List<String> nonReservedLockerNums = lockerDetailUseCase.findLockerDetailByLocker(locker).stream()
                            .filter(lockerDetail -> lockerDetail.getLockerDetailStatus().equals(NON_RESERVED))
                            .map(LockerDetail::getLockerNum)
                            .collect(Collectors.toList());
                    return LeftLockerInfo.builder()
                            .leftLockerName(locker.getName())
                            .leftLockerNum(nonReservedLockerNums)
                            .build();
                })
                .collect(Collectors.toList());
        return LeftLockerResponseDto.builder()
                .leftLockerInfo(leftLockerInfos)
                .build();
    }


    @Override
    public void modifyLockerInfo(ModifyLockerInfoReqeustDto reqeustDto) {
        Locker locker = lockerQueryPort.findByLockerId(reqeustDto.getLockerId()).orElseThrow(NotFoundLockerException::new);
        if (reqeustDto.getImageName() != null && reqeustDto.getImageUrl() != null) {
            locker.modifiedImageInfo(ImageInfo.builder()
                    .imageUrl(reqeustDto.getImageUrl())
                    .imageName(reqeustDto.getImageName())
                    .build());
        }
        if (reqeustDto.getStartTime() != null && reqeustDto.getEndTime() != null) {
            locker.modifiedDateTime(Period.builder()
                    .startDateTime(reqeustDto.getStartTime())
                    .endDateTime(reqeustDto.getEndTime())
                    .build());
        }
        if (!reqeustDto.getUserStates().isEmpty() || reqeustDto.getUserStates() != null) {
            locker.getPermitUserState().clear();
            reqeustDto.getUserStates().stream().forEach(userState -> locker.getPermitUserState().add(userState));
        }
        if (!reqeustDto.getUserTiers().isEmpty() || reqeustDto.getUserTiers() != null) {
            locker.getPermitUserTier().clear();
            reqeustDto.getUserTiers().stream().forEach(userTier -> locker.getPermitUserTier().add(userTier));
        }
    }

    @Override
    public List<AllLockersInMajorResponseDto> findAllLockerInMajor(FindAllLockerInMajorRequestDto requestDto) {
        User user = userUseCase.findByStudentNum(requestDto.getStudentNum());
        Major major = user.getMajorDetail().getMajor();
        List<Locker> lockerByUserMajor = lockerQueryPort.findLockerByUserMajor(major);
        return lockerByUserMajor.stream().map(locker ->
                AllLockersInMajorResponseDto.builder()
                        .lockerName(locker.getName())
                        .lockerDetailInfoList(
                                lockerDetailUseCase.findLockerDetailByLocker(locker).stream()
                                        .map(lockerDetail ->
                                                LockerDetailInfo.builder()
                                                        .lockerDetailStatus(lockerDetail.getLockerDetailStatus())
                                                        .locker_num(lockerDetail.getLockerNum())
                                                        .row_num(lockerDetail.getRow_num())
                                                        .column_num(lockerDetail.getColumn_num())
                                                        .build()).collect(Collectors.toList())
                        )
                        .startReservationTime(locker.getPeriod().getStartDateTime())
                        .endReservationTime(locker.getPeriod().getEndDateTime())
                        .build()

        ).collect(Collectors.toList());
    }

    @Override
    public void setLockerPeriod(LockerSetTimeRequestDto setPeriodRequestDto) {
     /*   List<Locker> lockers = lockerQueryPort.findAll();
        log.info("시간설정 시작");
        lockers.stream().parallel()
                .forEach(locker -> locker.modifiedDateTime(setPeriodRequestDto)
                );
        log.info("시간설정 완료");*/
    }

    @Transactional(readOnly = true)
    @Override
    public LockerPeriodResponseDto getLockerPeriod() {
        List<Locker> lockers = lockerQueryPort.findAll();
        Locker locker = lockers.get(0);
        if (locker.getPeriod() == null) {
            return LockerPeriodResponseDto.builder()
                    .endDateTime(null)
                    .startDateTime(null)
                    .build();
        } else {
            return LockerPeriodResponseDto.builder()
                    .startDateTime(locker.getPeriod().getStartDateTime())
                    .endDateTime(locker.getPeriod().getEndDateTime())
                    .build();
        }
    }

    @Override
    public List<Locker> findLockerByUserMajor(Major major) {
        return lockerQueryPort.findLockerByUserMajor(major);
    }

    @Override
    public LockerCreateResponseDto createLocker(LockerCreateRequestDto lockerCreateRequestDto, String studentNum) {
        User lockerCreater = userUseCase.findByStudentNum(studentNum);
        Locker createdLocker = Locker.createLocker(
                lockerCreateRequestDto.toLockerCreateDto(
                        lockerCreater.getMajorDetail().getMajor()
                )
        );
        Locker saveLocker = lockerQueryPort.save(createdLocker);
        createLockerDetail(lockerCreateRequestDto, saveLocker);
        return LockerCreateResponseDto.builder()
                .createdLockerName(saveLocker.getName())
                .build();

    }

    private void createLockerDetail(LockerCreateRequestDto lockerCreateRequestDto, Locker saveLocker) {
        lockerCreateRequestDto.getLockerDetailCreateRequests().stream().map(lockerDetailCreateRequest ->
                lockerDetailUseCase.saveLockerDetail(lockerDetailCreateRequest.toCreateDto(saveLocker))
        ).collect(Collectors.toList());
    }

}
