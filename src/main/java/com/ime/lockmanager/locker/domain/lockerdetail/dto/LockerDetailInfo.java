package com.ime.lockmanager.locker.domain.lockerdetail.dto;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetailStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LockerDetailInfo {
    private String row_num;
    private String column_num;
    private String locker_num;
    private LockerDetailStatus lockerDetailStatus;
}
