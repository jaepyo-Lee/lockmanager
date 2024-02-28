package com.ime.lockmanager.locker.application.service;


import com.ime.lockmanager.locker.application.port.in.LockerDetailUseCase;
import com.ime.lockmanager.locker.application.port.out.LockerDetailCommandPort;
import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetailStatus;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LockerDetailService implements LockerDetailUseCase {
    private final LockerDetailQueryPort lockerDetailQueryPort;
    private final LockerDetailCommandPort lockerDetailCommandPort;

    @Override
    public LockerDetail saveLockerDetail(LockerDetailCreateDto lockerDetailCreateDto) {
        return lockerDetailCommandPort.save(LockerDetail.builder()
                .locker(lockerDetailCreateDto.getLocker())
                .lockerNum(lockerDetailCreateDto.getLockerNum())
                .row_num(lockerDetailCreateDto.getRowNum())
                .column_num(lockerDetailCreateDto.getColumnNum())
                .lockerDetailStatus(LockerDetailStatus.NON_RESERVED)
                .build());
    }

    @Override
    public List<LockerDetail> findLockerDetailByLocker(Locker locker) {
        return lockerDetailQueryPort.findLockerDetailByLocker(locker.getId());
    }
}
