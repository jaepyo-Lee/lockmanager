package com.ime.lockmanager.locker.application.service;


import com.ime.lockmanager.locker.application.port.in.LockerDetailUseCase;
import com.ime.lockmanager.locker.application.port.in.dto.CreateLockerDetailDto;
import com.ime.lockmanager.locker.application.port.out.LockerDetailCommandPort;
import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetailStatus;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ime.lockmanager.locker.adapter.in.req.NumberIncreaseDirection.DOWN;

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
    public List<LockerDetail> findLockerDetailsByLocker(Locker locker) {
        return lockerDetailQueryPort.findLockerDetailByLocker(locker.getId());
    }


    @Override
    public void createLockerDetails(CreateLockerDetailDto createLockerDetailDto, Locker saveLocker) {
        int totalColumns = createLockerDetailDto.getTotalColumn();
        int totalRows = createLockerDetailDto.getTotalRow();
        List<LockerDetail> lockerDetails = null;
        if (createLockerDetailDto.getNumberIncreaseDirection().equals(DOWN)) {
            lockerDetails = makeLockerDetails(saveLocker, totalColumns, totalRows);
        } else {
            lockerDetails = makeLockerDetails(saveLocker, totalRows, totalColumns);
        }
        lockerDetailCommandPort.saveAll(lockerDetails);
    }

    private List<LockerDetail> makeLockerDetails(Locker saveLocker, int master, int slave) {
        List<LockerDetail> saveLockerDetails = new ArrayList<>();
        int num = 0;
        for (int i = 1; i <= master; i++) {
            for (int j = 1; j <= slave; j++) {
                saveLockerDetails.add(getLockerDetail(saveLocker, ++num, i, j));
            }
        }
        return saveLockerDetails;
    }

    private LockerDetail getLockerDetail(Locker saveLocker, int num, int i, int j) {
        return LockerDetail.builder()
                .locker(saveLocker)
                .lockerNum(Integer.toString(num))
                .row_num(Integer.toString(j))
                .column_num(Integer.toString(i))
                .lockerDetailStatus(LockerDetailStatus.NON_RESERVED)
                .build();
    }
}
