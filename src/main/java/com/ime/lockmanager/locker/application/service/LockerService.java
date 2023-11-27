package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.locker.application.port.in.LockerDetailUseCase;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.FindAllLockerInMajorRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerCreateRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.AllLockersInMajorResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerCreateResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetailStatus;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailInfo;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
class LockerService implements LockerUseCase {
    private final LockerQueryPort lockerQueryPort;
    private final LockerDetailUseCase lockerDetailUseCase;
    private final UserUseCase userUseCase;

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
                                                        .locker_num(lockerDetail.getLocker_num())
                                                        .row_num(lockerDetail.getRow_num())
                                                        .column_num(lockerDetail.getColumn_num())
                                                        .build()).collect(Collectors.toList())
                        )
                        .startReservationTime(locker.getPeriod().getStartDateTime())
                        .endReservationTime(locker.getPeriod().getStartDateTime())
                        .build()

        ).collect(Collectors.toList());
    }

    @Override
    public void setLockerPeriod(LockerSetTimeRequestDto setPeriodRequestDto) {
        List<Locker> lockers = lockerQueryPort.findAll();
        log.info("시간설정 시작");
        lockers.stream().parallel()
                .forEach(locker -> locker.modifiedDateTime(setPeriodRequestDto)
                );
        log.info("시간설정 완료");
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
