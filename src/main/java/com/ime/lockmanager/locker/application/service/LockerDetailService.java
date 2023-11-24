package com.ime.lockmanager.locker.application.service;


import com.ime.lockmanager.locker.application.port.in.LockerDetailUseCase;
import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.locker.domain.LockerDetail;
import com.ime.lockmanager.locker.domain.dto.LockerDetailCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LockerDetailService implements LockerDetailUseCase {
    private final LockerDetailQueryPort lockerDetailQueryPort;

    @Override
    public LockerDetail saveLockerDetail(LockerDetailCreateDto lockerDetailCreateDto) {
        return lockerDetailQueryPort.save(LockerDetail.builder()
                .locker(lockerDetailCreateDto.getLocker())
                .locker_num(lockerDetailCreateDto.getLockerNum())
                .row_num(lockerDetailCreateDto.getRowNum())
                .column_num(lockerDetailCreateDto.getColumnNum())
                .isUsable(lockerDetailCreateDto.isUsable())
                .build());
    }


}
