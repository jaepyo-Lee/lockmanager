package com.ime.lockmanager.locker.application.port.in.dto;

import com.ime.lockmanager.locker.adapter.in.req.NumberIncreaseDirection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateLockerDetailDto {
    private Integer totalRow;
    private Integer totalColumn;
    private NumberIncreaseDirection numberIncreaseDirection;
}
