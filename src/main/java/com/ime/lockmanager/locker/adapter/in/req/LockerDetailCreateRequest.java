package com.ime.lockmanager.locker.adapter.in.req;

import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.dto.LockerDetailCreateDto;
import lombok.Getter;

@Getter
public class LockerDetailCreateRequest {
    private String lockerNum;
    private String rowNum;

    private String columnNum;

    private boolean isUsable;
    public LockerDetailCreateDto toCreateDto(Locker locker){
        return LockerDetailCreateDto.builder()
                .locker(locker)
                .lockerNum(this.lockerNum)
                .columnNum(this.columnNum)
                .rowNum(this.rowNum)
                .isUsable(this.isUsable)
                .build();
    }
}
