package com.ime.lockmanager.locker.adapter.in.req;

import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetailStatus;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailCreateDto;
import lombok.Getter;

@Getter
public class LockerDetailCreateRequest {
    private String lockerNum;
    private String rowNum;

    private String columnNum;
    private LockerDetailStatus lockerDetailStatus;
    public LockerDetailCreateDto toCreateDto(Locker locker){
        return LockerDetailCreateDto.builder()
                .locker(locker)
                .lockerNum(this.lockerNum)
                .columnNum(this.columnNum)
                .rowNum(this.rowNum)
                .lockerDetailStatus(this.lockerDetailStatus)
                .build();
    }

}
