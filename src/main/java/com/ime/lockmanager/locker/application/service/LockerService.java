package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
class LockerService implements LockerUseCase  {
    private final LockerQueryPort lockerQueryPort;

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
        if(locker.getPeriod() == null){
            return LockerPeriodResponseDto.builder()
                    .endDateTime(null)
                    .startDateTime(null)
                    .build();
        } else{
            return LockerPeriodResponseDto.builder()
                    .startDateTime(locker.getPeriod().getStartDateTime())
                    .endDateTime(locker.getPeriod().getEndDateTime())
                    .build();
        }
    }
}
